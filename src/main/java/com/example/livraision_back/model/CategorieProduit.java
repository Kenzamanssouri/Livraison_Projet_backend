package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategorieProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    // Getters and Setters
}
