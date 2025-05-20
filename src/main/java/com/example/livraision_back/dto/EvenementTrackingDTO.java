package com.example.livraision_back.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EvenementTrackingDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String description;
}
