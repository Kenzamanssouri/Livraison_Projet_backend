package com.example.livraision_back.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
public class ParametresPlateforme {

    @Id
    private Long id = 1L;

    private Double fraisLivraison;
    private Double seuilBlocage;
    private Integer delaiRegularisation;
}

