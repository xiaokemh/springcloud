package com.geo.gateway.filter;

import com.geo.core.util.JSONUtil;
import com.geo.core.util.MD5Util;
import com.geo.core.util.RedisUtils;
import com.geo.core.util.TokenUtil;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final static Logger logger = LoggerFactory.getLogger(GlobalAuthFilter.class);

    // MD5加密
    public static final String MD5_KEY = "skzXcNWoeDfVSld";

    // token
    public static final String TOKEN_S = "token%s";

    // 3分钟超时限制
    public static final int TIMELIMIT = 3;
    /**
     * 入口url
     **/
    private List<String> gateUrls;
    /**
     * 白名单
     **/
    private List<String> excludeUrls;
    private boolean open;

    private RedisUtils redisUtils;

    public GlobalAuthFilter(List<String> gateUrls, List<String> excludeUrls, boolean open, RedisUtils redisUtils) {
        this.gateUrls = gateUrls;
        this.excludeUrls = excludeUrls;
        this.open = open;
        this.redisUtils = redisUtils;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        if (!open) {
            return chain.filter(exchange);
        }

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // Get Body
        GatewayContext gatewayContext = exchange.getAttribute(GatewayContext.CACHE_GATEWAY_CONTEXT);

        // 获取请求url
        String url = serverHttpRequest.getURI().getPath();

        // 获取远程请求ip插入请求头
        String remoteIP = this.getRemoteHost(serverHttpRequest);
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        builder.header("requestIP", remoteIP);
        exchange = exchange.mutate().request(builder.build()).build();


        // 过滤url
        if (!this.gate(url)) {
            return chain.filter(exchange);
        }

        if (this.exclude(url)) {
            return chain.filter(exchange);
        }

        // 跳过options请求
        if (HttpMethod.OPTIONS.equals(serverHttpRequest.getMethodValue().toUpperCase())) {
            return chain.filter(exchange);
        }

        // 去获取请求头数据
        HttpHeaders header = serverHttpRequest.getHeaders();
        String token = header.getFirst("token");
        String Md5Str = header.getFirst("Authorization");
        String timeStamp = header.getFirst("timeStamp");


        // 校验token
        if (StringUtils.isEmpty(token)) {
            logger.error("请求Token丢失，拒绝 ...{}", url);
            return errorRequest(exchange, "Token丢失，拒绝 ...");
        }

        // 校验token时效
        if (!redisUtils.hasKey(String.format(TOKEN_S, token))) {
            logger.error("无效Token，拒绝 ...{}", url);
            return errorRequest(exchange, "无效Token，拒绝 ...");
        }

        try {
            Map<String, Object> map = TokenUtil.validateToken(token);
            String tokenIP = map.get("ip") == null ? "" : map.get("ip").toString();
            // 校验ip
            if (!tokenIP.equals(remoteIP)) {
                logger.error("非法ip，拒绝 ...{},{},{}", url,remoteIP,tokenIP);
                return errorRequest(exchange, "非法ip，拒绝 ...");
            }
        } catch (Exception e) {
            return errorRequest(exchange, e.getMessage());
        }

        // 校验时间戳
        if (StringUtils.isEmpty(timeStamp)) {
            logger.error("请求头缺少timeStamp...{}", url);
            return errorRequest(exchange, "请求头缺少timeStamp...");
        }
        long curent = System.currentTimeMillis();
        long during = (curent - Long.parseLong(timeStamp)) / (1000 * 60);
        if (during > TIMELIMIT) {
            logger.error("超时请求，拒绝...{}", url);
            return errorRequest(exchange, "超时请求，拒绝...");
        }

        //request请求一个变俩，一个请求校验MD5一个转发
//        List<ServerHttpRequest> requests = cloneRequest(serverHttpRequest, 2);
        try {
            // 校验Md5
//            serverHttpRequest = requests.get(0);
            this.checkMD5Str(serverHttpRequest, Md5Str, timeStamp, gatewayContext);
            logger.info("MD5校验成功！！！！！！！！ -->{}", serverHttpRequest.getURI());
        } catch (Exception e) {
            logger.error("MD5校验失败！！！！！！！{}", url);
            return errorRequest(exchange, e.getMessage());
        }

//        serverHttpRequest = requests.get(1);
//        exchange = exchange.mutate().request(serverHttpRequest).build();
        logger.info("GlobalAuthFilter request url over -->{}", serverHttpRequest.getURI());
        return chain.filter(exchange);
    }

    private Mono<Void> errorRequest(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private boolean gate(String url) {
        if (gateUrls != null) {
            for (String exc : gateUrls) {
                if (url.startsWith(exc)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exclude(String url) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (url.startsWith(exc)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @Description 校验MD5字符串
     * @Author chenjh
     **/
    private void checkMD5Str(ServerHttpRequest serverHttpRequest, String md5Str, String timeStamp, GatewayContext gatewayContext) throws AuthException {

        // 获取请求参数排序后的Josn字符串
        String paramStr = this.getParamStr(serverHttpRequest, gatewayContext);
        logger.info("post请求{}参数json字符串==={}", serverHttpRequest.getURI(), paramStr);
        //  校验MD5加密
        String newMd5 = MD5Util.MD5Encode(paramStr + timeStamp, "", MD5_KEY);

        if (!newMd5.equals(md5Str)) {
            throw new AuthException("MD5 校验失败！！！");
        }
    }

    /**
     * @Description 获取请求参数排序后的json字符串
     * @Author chenjh
     **/
    private String getParamStr(ServerHttpRequest serverHttpRequest, GatewayContext gatewayContext) throws AuthException {
        // 获取请求方法名
        String method = serverHttpRequest.getMethodValue();

        TreeMap<String, String> params = null;
        if (HttpMethod.GET.name().equals(method.toUpperCase())) {
            Map<String, String> requestQueryParams = this.resolveBodyFromGetRequest(serverHttpRequest);
            if (!CollectionUtils.isEmpty(requestQueryParams)) {
                params = new TreeMap<>(requestQueryParams);
            }
        } else if (HttpMethod.POST.name().equals(method.toUpperCase())) {
            Map<String, String> requestQueryParams = JSONUtil.toMap(gatewayContext.getCacheBody());
            if (!CollectionUtils.isEmpty(requestQueryParams)) {
                params = new TreeMap<>(requestQueryParams);
            }
        } else {
            throw new AuthException(method + "请求不支持");
        }

        return CollectionUtils.isEmpty(params) ? "" : JSONUtil.toJson(params);
    }

//        /**
//     * @Description 克隆请求（解决一个请求只能获取一次请求体的问题）
//     * @Author chenjh
//     **/
//    private List<ServerHttpRequest> cloneRequest(ServerHttpRequest serverHttpRequest, int num) {
//        //获取请求体
//        String bodyStr = this.getStringBuilder(serverHttpRequest);
//
//        // 重新new一个serverHttpRequest返给serverHttpRequest
//        List<ServerHttpRequest> requests = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
//            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//            ServerHttpRequest request = new ServerHttpRequestDecorator(serverHttpRequest) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return bodyFlux;
//                }
//            };
//            requests.add(request);
//        }
//        return requests;
//    }

    /**
     * @Description post请求获取参数
     * @Author chenjh
     **/
    private Map<String, String> resolveBodyFromPostRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        String bodyStr = getStringBuilder(serverHttpRequest);

        if (StringUtils.isEmpty(bodyStr)) {
            return null;
        }
        bodyStr = this.getString(bodyStr);

        return JSONUtil.toMap(bodyStr);
    }

    /**
     * @Description 获取请求体
     * @Author chenjh
     **/
    private String getStringBuilder(ServerHttpRequest serverHttpRequest) {
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
//        Flux<DataBuffer> body = serverHttpRequest.getBody();
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//        body.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            DataBufferUtils.release(buffer);
//            bodyRef.set(charBuffer.toString());
//        });
//        return bodyRef.get();
    }

    /**
     * @Description 处理json字符串
     * @Author chenjh
     **/
    private String getString(String bodyStr) {
        bodyStr = bodyStr.replace(":", ":\"");
        bodyStr = bodyStr.replace(",", "\",");
        bodyStr = bodyStr.replace("}", "\"}");
        bodyStr = bodyStr.replace("\"\"", "\"");
        return bodyStr;
    }

    protected DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * @return Map<String, String>
     */
    private Map<String, String> resolveBodyFromGetRequest(ServerHttpRequest serverHttpRequest) {
        MultiValueMap<String, String> requestQueryParams = serverHttpRequest.getQueryParams();
        if (CollectionUtils.isEmpty(requestQueryParams)) {
            return null;
        }
        Map<String, String> res = new HashMap<>();
        requestQueryParams.forEach((k, v) -> {
            res.put(k, v.get(0));
        });

        return res;
    }

    public String getRemoteHost(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    // 异常
    private static class AuthException extends Exception {

        public AuthException(String s) {
            super(s);
        }

    }
}
