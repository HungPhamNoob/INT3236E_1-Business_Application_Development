package com.example.demo.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationInitConfig {
     @Bean
     ApplicationRunner applicationRunner() {
         return args -> {
             System.out.println("🚀 ApplicationInitConfig đã chạy!");
         };
     }
}