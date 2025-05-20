package com.example.livraision_back.service.impl;



import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.mapper.NotificationMapper;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper clientMapper;


    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper clientMapper) {
        this.notificationRepository = notificationRepository;
        this.clientMapper = clientMapper;
    }
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
}
