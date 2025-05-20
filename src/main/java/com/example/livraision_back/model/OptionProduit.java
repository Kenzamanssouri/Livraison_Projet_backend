package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
@Entity
public class OptionProduit {
    private String nom;

    @ElementCollection
    private List<String> valeurs;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Getters and Setters
}
