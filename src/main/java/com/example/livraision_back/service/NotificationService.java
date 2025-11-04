package com.example.livraision_back.service;


import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
    public List<Notification> getUnopenedNotifications();
    public void markAsOpened( Long id);
    public Notification findById(Long id);
    public Notification save(Notification notification);
}
