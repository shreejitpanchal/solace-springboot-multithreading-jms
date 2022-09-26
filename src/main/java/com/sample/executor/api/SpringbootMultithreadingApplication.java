package com.sample.executor.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class SpringbootMultithreadingApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultithreadingApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
