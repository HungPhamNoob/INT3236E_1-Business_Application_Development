package com.example.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.producer.model.User;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private static final String TOPIC = "user-topic";
    private final KafkaTemplate<String, User> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(User user) {
        CompletableFuture<SendResult<String, User>> future =
                kafkaTemplate.send(TOPIC, user.getId(), user);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("✅ Sent message=[" + user +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("❌ Unable to send message=[" +
                        user + "] due to: " + ex.getMessage());
            }
        });
    }
}