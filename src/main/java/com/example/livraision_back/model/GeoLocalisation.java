package com.example.livraision_back.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GeoLocalisation {
    private Double latitude;
    private Double longitude;

    // Getters and Setters
}
