package com.example.livraision_back.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GeoLocalisation {
    private double latitude;
    private double longitude;

    // Getters and Setters
}
