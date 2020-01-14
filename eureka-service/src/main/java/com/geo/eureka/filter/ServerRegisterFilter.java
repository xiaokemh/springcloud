package com.geo.eureka.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@ConfigurationProperties("spring.filter")
@Component
public class ServerRegisterFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(ServerRegisterFilter.class);

    private static final String EXCULD_URL = "/eureka/apps";

    private List<String> excludeIPs;

    public List<String> getExcludeIPs() {
        return excludeIPs;
    }

    public void setExcludeIPs(List<String> excludeIPs) {
        this.excludeIPs = excludeIPs;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        for (String excludeIP : excludeIPs) {
            logger.info("excludeIP========================{}", excludeIP);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        //业务实现，根据请求的IP或者参数判断是否可以执行注册或者访问
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(uri)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        if (uri.startsWith(EXCULD_URL)) {
            String remoteHost = this.getRemoteHost(request);
            if (!exclude(remoteHost)) {
                logger.info("ServerRegistryInterceptor deal with ip===={},unpass", remoteHost);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        }
        logger.info("ServerRegistryInterceptor deal with ip===={},pass", request.getRemoteAddr());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean exclude(String ip) {
        if (excludeIPs != null) {
            for (String exc : excludeIPs) {
                if (ip.startsWith(exc)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
