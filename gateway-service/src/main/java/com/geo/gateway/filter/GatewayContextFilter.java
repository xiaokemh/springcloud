package com.geo.gateway.filter;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * 缓存post请求参数
 *
 * @author
 */
@Component
public class GatewayContextFilter implements GlobalFilter, Ordered {

    private final static Logger log = LoggerFactory.getLogger(GatewayContextFilter.class);

    private static final ResolvableType MULTIPART_DATA_TYPE = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);

    private static final Mono<MultiValueMap<String, Part>> EMPTY_MULTIPART_DATA = Mono.just(CollectionUtils.unmodifiableMultiValueMap(new LinkedMultiValueMap<String, Part>(0))).cache();
    /**
     * default HttpMessageReader.
     */
    private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // save request path and serviceId into gateway context
        final ServerHttpRequest request = exchange.getRequest();
        if(!HttpMethod.POST.name().equalsIgnoreCase(request.getMethodValue())){
            return chain.filter(exchange);
        }

        final String path = request.getPath().pathWithinApplication().value();
        final GatewayContext gatewayContext = new GatewayContext();
        gatewayContext.setPath(path);
        // save gateway context into exchange
        exchange.getAttributes().put(GatewayContext.CACHE_GATEWAY_CONTEXT, gatewayContext);
        final HttpHeaders headers = request.getHeaders();
        final MediaType contentType = headers.getContentType();
        final long contentLength = headers.getContentLength();
        if (contentLength > 0) {
            if (contentType.toString().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                return readFormData(exchange, chain, gatewayContext);
            } else {
                return readBody(exchange, chain, gatewayContext);
            }
        }
        log.debug("[GatewayContext]ContentType:{},Gateway context is set with {}", contentType, gatewayContext);
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -10;
    }

    /**
     * ReadFormData.
     *
     * @param exchange exchange
     * @param chain chain
     * @return Mono
     */
    private Mono<Void> readFormData(ServerWebExchange exchange, GatewayFilterChain chain, GatewayContext gatewayContext) {
        return exchange.getRequest().getBody().collectList().flatMap(dataBuffers -> {
            final byte[] totalBytes = dataBuffers.stream().map(dataBuffer -> {
                try {
                    final byte[] bytes = IOUtils.toByteArray(dataBuffer.asInputStream());
                    return bytes;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).reduce(this::addBytes).get();
            final ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return Flux.just(buffer(totalBytes));
                }
            };
            final ServerCodecConfigurer configurer = ServerCodecConfigurer.create();
            final Mono<MultiValueMap<String, Part>> multiValueMapMono = repackageMultipartData(decorator, configurer);
            return multiValueMapMono.flatMap(part -> {
                TreeMap<String,String> map = new TreeMap<>();
                for (String key : part.keySet()) {
                    // 如果为文件时 则进入下一次循环
                    if (key.contains("file")) {
                        continue;
                    }
                    StringBuilder sb = new StringBuilder();
                    part.getFirst(key).content().subscribe(buffer -> {
                        byte[] bytes = new byte[buffer.readableByteCount()];
                        buffer.read(bytes);
                        DataBufferUtils.release(buffer);
                        String bodyString = new String(bytes, StandardCharsets.UTF_8);
                        sb.append(bodyString);
                    });
                    map.put(key, sb.toString());
                }
                gatewayContext.setCacheBody(JSON.toJSONString(map));
                return chain.filter(exchange.mutate().request(decorator).build());
            });
        });
    }

    /**
     * ReadJsonBody.
     *
     * @param exchange exchange
     * @param chain chain
     * @return Mono
     */
    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain, GatewayContext gatewayContext) {
        // join the body
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            // read the body Flux<Databuffer>
            DataBufferUtils.retain(dataBuffer);
            final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
            // repackage ServerHttpRequest
            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            // mutate exchage with new ServerHttpRequest
            final ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            // read body string with default messageReaders
            return ServerRequest.create(mutatedExchange, messageReaders).bodyToMono(String.class).doOnNext(objectValue -> {
                gatewayContext.setCacheBody(objectValue);
                log.debug("[GatewayContext]Read JsonBody:{}", objectValue);
            }).then(chain.filter(mutatedExchange));
        });
    }

    private DataBuffer buffer(byte[] bytes) {
        final NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        final DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    @SuppressWarnings("unchecked")
    private static Mono<MultiValueMap<String, Part>> repackageMultipartData(ServerHttpRequest request, ServerCodecConfigurer configurer) {
        try {
            final MediaType contentType = request.getHeaders().getContentType();
            if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
                return ((HttpMessageReader<MultiValueMap<String, Part>>) configurer.getReaders().stream().filter(reader -> reader.canRead(MULTIPART_DATA_TYPE, MediaType.MULTIPART_FORM_DATA))
                        .findFirst().orElseThrow(() -> new IllegalStateException("No multipart HttpMessageReader."))).readMono(MULTIPART_DATA_TYPE, request, Collections.emptyMap())
                        .switchIfEmpty(EMPTY_MULTIPART_DATA).cache();
            }
        } catch (InvalidMediaTypeException ex) {
            // Ignore
        }
        return EMPTY_MULTIPART_DATA;
    }

    /**
     * addBytes.
     * @param first first
     * @param second second
     * @return byte
     */
    public byte[] addBytes(byte[] first, byte[] second) {
        final byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}