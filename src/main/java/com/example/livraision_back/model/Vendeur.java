package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Vendeur extends Utilisateur {
    private String nomEtablissement;
    private String categorie;
    private String registreCommerce;
    private String identifiantFiscal;
    private String rib;
    private boolean estValideParAdmin;
    private String motifRejet;
    @Embedded
    private Horaire horaireOuverture;

    @OneToMany(mappedBy = "vendeur")
    private List<Produit> produits;

    @OneToMany(mappedBy = "vendeur")
    private List<Commande> commandes;


    // Getters and Setters
    public List<Produit> getProduits() {
        return produits;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public Horaire getHoraireOuverture() {
        return horaireOuverture;
    }

    public void setHoraireOuverture(Horaire horaireOuverture) {
        this.horaireOuverture = horaireOuverture;
    }
}
