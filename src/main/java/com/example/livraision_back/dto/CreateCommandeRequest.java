package com.example.livraision_back.dto;

import lombok.Data;

@Data
public class CreateCommandeRequest {
    private String description;   // texte libre
    private String adresse;       // adresse de livraison
    private String username;       // adresse de livraison
}

