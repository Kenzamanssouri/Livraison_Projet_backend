package com.example.livraision_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Horaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jour; // un seul jour par horaire
    private String heureOuverture;
    private String heureFermeture;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    @JsonBackReference
    private Vendeur vendeur;
}
