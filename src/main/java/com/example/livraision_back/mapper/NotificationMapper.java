package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.NotificationDTO;
import com.example.livraision_back.model.Notification;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
@Mapper(componentModel = "spring")

public interface NotificationMapper {
    NotificationDTO toDTO(Notification client);

    // Custom implementation to include password hashing
    default Notification toEntity(NotificationDTO dto, PasswordEncoder passwordEncoder) {
        if (dto == null) {
            return null;
        }

        Notification client = new Notification();
        client.setObject(dto.getObject());
        client.setIdObject(dto.getIdObject());
        client.setMessage(dto.getMessage());




        return client;
    }
}
