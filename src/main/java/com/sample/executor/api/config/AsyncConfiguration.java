package com.sample.executor.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Value("${multithread.coreThreadPoolSize}")
    private int coreThreadPoolSize;
    @Value("${multithread.maxThreadPoolSize}")
    private int maxThreadPoolSize;
    @Value("${multithread.maxQueueThreadCapacity}")
    private int maxQueueThreadCapacity;
    @Value("${multithread.threadNamePrefix}")
    private String threadNamePrefix;


    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreThreadPoolSize); // Considering 1 VPU = 1 Thread
        executor.setMaxPoolSize(maxThreadPoolSize);   // Considering 1 VPU = 1 Thread
        executor.setQueueCapacity(maxQueueThreadCapacity); // Need to keep significantly high incase of Async CompletableFuture
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}

