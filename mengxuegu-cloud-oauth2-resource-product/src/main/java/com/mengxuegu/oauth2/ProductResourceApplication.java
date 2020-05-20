package com.mengxuegu.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProductResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductResourceApplication.class, args);
    }
}
