package com.fuqqqq.common.area.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService scheduledThreadPool() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }
}
