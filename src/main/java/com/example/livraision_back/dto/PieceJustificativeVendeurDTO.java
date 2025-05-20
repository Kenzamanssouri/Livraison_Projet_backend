package com.example.livraision_back.dto;


import jakarta.persistence.Lob;
import lombok.Data;

@Data

public class PieceJustificativeVendeurDTO {
    private Long id;

    private String nomFichier;
    private String typeFichier; // Exemple : "RC", "CNSS", "PATENTE"
    private String cheminFichier;



    private VendeurDTO vendeur;
    @Lob
    private byte[] contenu;
}
