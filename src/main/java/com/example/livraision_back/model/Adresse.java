package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Adresse {
    private String rue;
    private String ville;
    private String codePostal;

    @Embedded
    private GeoLocalisation localisation;

    // Getters and Setters
}
