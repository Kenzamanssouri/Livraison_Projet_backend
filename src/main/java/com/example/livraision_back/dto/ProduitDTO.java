package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private String image;
    private VendeurDTO vendeur;
    private List<OptionProduitDTO> options;
    private CategorieProduitDTO categorie;
    private Boolean actif;
}
