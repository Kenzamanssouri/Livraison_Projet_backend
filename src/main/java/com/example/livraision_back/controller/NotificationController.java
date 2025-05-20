package com.example.livraision_back.controller;

import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService clientService;

    public NotificationController(NotificationService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
