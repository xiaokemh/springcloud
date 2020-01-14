package com.geo.gateway.config;

import com.geo.core.util.RedisUtils;
import com.geo.gateway.filter.GlobalAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private ExcludesConfig excludesConfig;
    @Autowired
    private RedisUtils redisUtils;

    @Bean
    public GlobalAuthFilter getGlobalAuthFilter() {
        return new GlobalAuthFilter(excludesConfig.getGateUrls(), excludesConfig.getExcludeUrls(),
                excludesConfig.getOpen(), redisUtils);
    }

}
