package com.example.livraision_back.controller;

import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.model.RoleUtilisateur;
import com.example.livraision_back.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }
    //@GetMapping
    //public ResponseEntity<List<Notification>> getAllNotifications() {
    //    List<Notification> clients = service.findAll();
    //    return new ResponseEntity<>(clients, HttpStatus.OK);
    //}
    @GetMapping
    public List<Notification> getUnopenedNotifications() {
        return service.getUnopenedNotifications();
    }
    @PutMapping("/{id}/open")
    public void markAsOpened(@PathVariable Long id) {
        Notification notification = service.findById(id);
        notification.setOpened(true);
        service.save(notification);
    }

    @GetMapping("/notifications/livreur")
    public List<Notification> getNotificationsLivreur() {
        return service.findByRoleAndLuFalse(RoleUtilisateur.LIVREUR);
    }
    @GetMapping("/livreur")
    public Page<NotificationDTO> getLivreurNotifications(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return service.findByRole(RoleUtilisateur.LIVREUR, pageable);
    }

    @GetMapping("/livreur/unread")
    public Page<NotificationDTO> getUnreadLivreurNotifications(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return service.findByRoleAndLuFalse(RoleUtilisateur.LIVREUR, pageable);
    }
    @GetMapping("/livreur/unread/count")
    public long countUnreadLivreur() {
        return service.countUnread(RoleUtilisateur.LIVREUR);
    }
}
