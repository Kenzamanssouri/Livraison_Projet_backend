package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;

@Data
public class HoraireDTO {
    private Long id;

    private String jour;
    private String heureOuverture;
    private String heureFermeture;
}
