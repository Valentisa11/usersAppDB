package com.example.service;


import com.example.model.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void sendEvent(UserEvent event) {
        kafkaTemplate.send(topic, event);
    }
}