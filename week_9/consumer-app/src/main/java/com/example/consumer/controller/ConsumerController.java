package com.example.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    @GetMapping("/health")
    public String health() {
        return "Consumer is running and listening to Kafka messages!";
    }
}