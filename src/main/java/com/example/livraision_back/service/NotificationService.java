package com.example.livraision_back.service;


import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();

}
