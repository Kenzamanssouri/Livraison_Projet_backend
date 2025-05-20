package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produit produit;

    private int quantite;
    private double prixUnitaire;

    @ElementCollection
    private List<String> optionsChoisies;

    // Getters and Setters
}
