package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;

@Data
public class OptionProduitDTO {
    private String nom;
    private List<String> valeurs;
}
