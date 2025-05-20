package com.example.controller;

import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendNotification(
            @RequestParam String email,
            @RequestParam String operation) {
        String message = "CREATE".equals(operation)
                ? "Аккаунт создан"
                : "Аккаунт удалён";
        emailService.sendEmail(email, message);
        return ResponseEntity.ok().build();
    }
}