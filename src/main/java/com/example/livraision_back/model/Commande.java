package com.example.livraision_back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference // ← côté enfant, sera ignoré lors de la sérialisation

    private Client client;

    @ManyToOne
    private Vendeur vendeur;

    @ManyToOne
    private Livreur livreur;

    private LocalDateTime dateCommande;
    private LocalDateTime dateLivraisonEstimee;

    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;

   private String livraisonAdresse;

    @OneToOne(cascade = CascadeType.ALL)
    private TrackingCommande tracking;

    private double total;
    private String modePaiement;

    // Getters and Setters
}
