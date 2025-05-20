package com.example.livraision_back.dto;

import lombok.Data;

@Data

public class NotificationDTO {
    private Long id;

    private String message;
    private Long idObject;
    private String Object;
}
