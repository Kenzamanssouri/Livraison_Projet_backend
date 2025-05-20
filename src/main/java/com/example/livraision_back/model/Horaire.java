package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Horaire {
    private String jour;
    private String heureOuverture;
    private String heureFermeture;

    // Getters and Setters
}
