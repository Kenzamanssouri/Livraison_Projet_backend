package com.example.livraision_back.service;


import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.model.RoleUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
    public List<Notification> getUnopenedNotifications();
    public void markAsOpened( Long id);
    public Notification findById(Long id);
    public Notification save(Notification notification);
    List<Notification> findByRoleAndLuFalse(RoleUtilisateur role);
    Page<NotificationDTO> findByRole(RoleUtilisateur roleUtilisateur, Pageable pageable);
    Page<NotificationDTO> findByRoleAndLuFalse(RoleUtilisateur roleUtilisateur, Pageable pageable);

    long countUnread(RoleUtilisateur role);
}
