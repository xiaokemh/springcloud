package com.geo.gateway.config;

import com.geo.gateway.resolver.ApiKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 限流配置
 *
 * @author yyq
 */
@Configuration
public class LimitConfig {

    /**
     * 请求地址url限流
     *
     * @return
     */
    @Bean(name = ApiKeyResolver.BEAN_NAME)
    public ApiKeyResolver apiKeyResolver() {
        return new ApiKeyResolver();
    }
}
