package com.example.listener;

import com.example.model.UserEvent;
import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "${kafka.topic}", groupId = "notification-group")
    public void handleEvent(UserEvent event) {
        String message = "";
        switch (event.getOperation()) {
            case "CREATE":
                message = "Здравствуйте! Ваш аккаунт успешно создан!";
                break;
            case "DELETE":
                message = "Здравствуйте! Ваш аккаунт был удалён.";
                break;
        }
        emailService.sendEmail(event.getEmail(), message);
    }
}