package com.example.livraision_back.model;

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
