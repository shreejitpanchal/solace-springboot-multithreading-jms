package com.solace.executor.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class SpringbootMultithreadingJMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultithreadingJMSApplication.class, args);
    }

}
