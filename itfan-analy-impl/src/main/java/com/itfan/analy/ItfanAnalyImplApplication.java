package com.itfan.analy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ItfanAnalyImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItfanAnalyImplApplication.class, args);
    }
}
