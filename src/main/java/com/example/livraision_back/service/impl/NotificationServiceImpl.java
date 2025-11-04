package com.example.livraision_back.service.impl;



import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.mapper.NotificationMapper;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
    public List<Notification> getUnopenedNotifications() {
        return notificationRepository.findByOpenedFalse();
    }
    public void markAsOpened( Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setOpened(true);
        notificationRepository.save(notification);
    }
    public Notification findById(Long id){
        return notificationRepository.findById(id).get();
    }
    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }
}




