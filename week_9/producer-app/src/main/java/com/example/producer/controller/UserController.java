package com.example.producer.controller;

import org.springframework.web.bind.annotation.*;

import com.example.producer.model.User;
import com.example.producer.service.KafkaProducer;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final KafkaProducer kafkaProducer;

    public UserController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        kafkaProducer.sendUser(user);
        return "User sent to Kafka: " + user.toString();
    }

    @PostMapping("/random")
    public String createRandomUser() {
        String id = "user_" + System.currentTimeMillis();
        User user = new User(id, "User_" + id, "user" + id + "@example.com");
        kafkaProducer.sendUser(user);
        return "Random user sent to Kafka: " + user.toString();
    }

    @GetMapping("/health")
    public String health() {
        return "Producer is running!";
    }
}