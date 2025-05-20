package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private Double prix;
    private String image;

    @ManyToOne
    private Vendeur vendeur;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OptionProduit> options;

    @ManyToOne
    private CategorieProduit categorie;

    // Getters and Setters
}
