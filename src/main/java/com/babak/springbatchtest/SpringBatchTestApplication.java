package com.babak.springbatchtest;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBatchTestApplication {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchTestApplication.class, args);
    }

}
