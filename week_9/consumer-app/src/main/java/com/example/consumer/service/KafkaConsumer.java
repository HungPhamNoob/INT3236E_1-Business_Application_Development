package com.example.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.consumer.model.User;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "user-topic", groupId = "user-group")
    public void consume(User user) {
        System.out.println("✅ Received User: " + user.toString());
        processUser(user);
    }

    private void processUser(User user) {
        System.out.println("🔄 Processing user: " + user.getName());
        try {
            Thread.sleep(1000);
            System.out.println("✅ Successfully processed user: " + user.getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Error processing user: " + e.getMessage());
        }
    }
}