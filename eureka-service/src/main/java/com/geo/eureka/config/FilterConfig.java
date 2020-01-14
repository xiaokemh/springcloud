package com.geo.eureka.config;

import com.geo.eureka.filter.ServerRegisterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private ServerRegisterFilter serverRegisterFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(serverRegisterFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
