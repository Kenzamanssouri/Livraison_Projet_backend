package com.example.livraision_back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client extends Utilisateur {
    @OneToMany(mappedBy = "client")
    @JsonManagedReference // ← côté parent

    private List<Commande> commandes;

    // Getters and Setters
}
