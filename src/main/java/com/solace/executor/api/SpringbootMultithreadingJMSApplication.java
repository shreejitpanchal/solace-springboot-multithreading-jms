package com.solace.executor.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j // lombok
@SpringBootApplication
public class SpringbootMultithreadingJMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultithreadingJMSApplication.class, args);
    }

}
