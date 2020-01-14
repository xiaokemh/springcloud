package com.geo.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("spring.cloud.gateway.globalauthfilter")
@Component
public class ExcludesConfig {

    private List<String> gateUrls = new ArrayList<>();

    private List<String> excludeUrls = new ArrayList<>();

    private boolean open;

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<String> getGateUrls() {
        return gateUrls;
    }

    public void setGateUrls(List<String> gateUrls) {
        this.gateUrls = gateUrls;
    }
}
