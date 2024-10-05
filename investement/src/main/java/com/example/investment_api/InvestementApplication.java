package com.example.investment_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InvestementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestementApplication.class, args);
    }

}
