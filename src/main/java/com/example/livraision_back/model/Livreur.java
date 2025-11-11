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

    private double commissionTotale;        // Total des commissions gagnées
    private double encaissementsTotaux;     // Total des montants encaissés (paiement à la livraison)
    private LocalDate dateDernierEncaissement;

    private boolean bloque;                 // Livreur bloqué s’il ne régularise pas
    private Boolean estValideParAdmin;      // Compte validé par l’administrateur
    private String motifRejet;              // Motif de rejet si refusé par admin

    // 💰 Dépôt de garantie versé à l’inscription
    private Double depotGarantie;
    private String matriculeVehicule;

    /**
     * Solde net = commission - encaissements
     */
    public double getSoldeNet() {
        return commissionTotale - encaissementsTotaux;
    }

    /**
     * Solde total tenant compte du dépôt de garantie.
     */
    public double getSoldeAvecDepot() {
        return getSoldeNet() + depotGarantie;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public GeoLocalisation getPositionActuelle() {
        return positionActuelle;
    }

    public void setPositionActuelle(GeoLocalisation positionActuelle) {
        this.positionActuelle = positionActuelle;
    }

    public List<Commande> getCommandesLivrees() {
        return commandesLivrees;
    }

    public void setCommandesLivrees(List<Commande> commandesLivrees) {
        this.commandesLivrees = commandesLivrees;
    }

    public double getCommissionTotale() {
        return commissionTotale;
    }

    public void setCommissionTotale(double commissionTotale) {
        this.commissionTotale = commissionTotale;
    }

    public double getEncaissementsTotaux() {
        return encaissementsTotaux;
    }

    public void setEncaissementsTotaux(double encaissementsTotaux) {
        this.encaissementsTotaux = encaissementsTotaux;
    }

    public LocalDate getDateDernierEncaissement() {
        return dateDernierEncaissement;
    }

    public void setDateDernierEncaissement(LocalDate dateDernierEncaissement) {
        this.dateDernierEncaissement = dateDernierEncaissement;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public Boolean getEstValideParAdmin() {
        return estValideParAdmin;
    }

    public void setEstValideParAdmin(Boolean estValideParAdmin) {
        this.estValideParAdmin = estValideParAdmin;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public Double getDepotGarantie() {
        return depotGarantie;
    }

    public void setDepotGarantie(Double depotGarantie) {
        this.depotGarantie = depotGarantie;
    }
}
