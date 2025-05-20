package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PieceJustificativeVendeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFichier;
    private String typeFichier; // Exemple : "RC", "CNSS", "PATENTE"
    private String cheminFichier;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private Vendeur vendeur;


    @Lob
    private byte[] contenu;
}
