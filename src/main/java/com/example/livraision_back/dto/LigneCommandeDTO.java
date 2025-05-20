package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;
@Data
public class LigneCommandeDTO {
    private Long id;
    private Long produitId;
    private int quantite;
    private double prixUnitaire;
    private List<String> optionsChoisies;
}
