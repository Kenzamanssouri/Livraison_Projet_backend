package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrackingCommandeDTO {
    private Long id;
    private List<EvenementTrackingDTO> evenements;
}
