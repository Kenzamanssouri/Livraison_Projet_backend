package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client extends Utilisateur {
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;

    // Getters and Setters
}
