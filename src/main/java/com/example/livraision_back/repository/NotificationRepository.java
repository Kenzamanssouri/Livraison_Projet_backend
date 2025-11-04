package com.example.livraision_back.repository;

import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    List<Notification> findByOpenedFalse(); // Fetch only non-opened
}
