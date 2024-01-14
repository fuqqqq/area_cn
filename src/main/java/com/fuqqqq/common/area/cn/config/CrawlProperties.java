package com.fuqqqq.common.area.cn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = CrawlProperties.PREFIX)
@Data
public class CrawlProperties {
    public static final String PREFIX = "crawl";


    private String rootUrl;
    private List<String> regex;
}
