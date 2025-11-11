package com.example.livraision_back.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Vendeur extends Utilisateur {

    private String nomEtablissement;

    @Enumerated(EnumType.STRING)
    private CategorieVendeur categorie; // utilisation de l’enum

    private String registreCommerce;
    private String identifiantFiscal;
    private String rib;

    private Boolean estValideParAdmin;
    private String motifRejet;

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Horaire> horairesOuverture;

    @OneToMany(mappedBy = "vendeur")
    private List<Produit> produits;

    @OneToMany(mappedBy = "vendeur")
    private List<Commande> commandes;

    private Boolean bloque;
}
