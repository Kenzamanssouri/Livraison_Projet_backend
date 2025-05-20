package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Livreur extends Utilisateur {
    private boolean disponible;

    @Embedded
    private GeoLocalisation positionActuelle;

    @OneToMany(mappedBy = "livreur")
    private List<Commande> commandesLivrees;

    private double commissionTotale;
    private double encaissementsTotaux;
    private LocalDate dateDernierEncaissement;
    private boolean bloque;

    public double getSoldeNet() {
        return commissionTotale - encaissementsTotaux;
    }

    // Getters and Setters
}
