package com.example.livraision_back.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class LivreurDTO extends UtilisateurDTO {

    private boolean disponible;
    private GeoLocalisationDTO positionActuelle;
    private List<Long> commandeIds;            // IDs des commandes livrées

    private double commissionTotale;           // Total des commissions gagnées
    private double encaissementsTotaux;        // Total des montants encaissés
    private LocalDate dateDernierEncaissement;

    private boolean bloque;
    private Boolean estValideParAdmin;
    private String motifRejet;

    private Double depotGarantie;              // 💰 Dépôt de garantie du livreur

    private double soldeNet;                   // Commission - encaissements
    private double soldeAvecDepot;             // Solde net + dépôt de garantie

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public GeoLocalisationDTO getPositionActuelle() {
        return positionActuelle;
    }

    public void setPositionActuelle(GeoLocalisationDTO positionActuelle) {
        this.positionActuelle = positionActuelle;
    }

    public List<Long> getCommandeIds() {
        return commandeIds;
    }

    public void setCommandeIds(List<Long> commandeIds) {
        this.commandeIds = commandeIds;
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

    public double getSoldeNet() {
        return soldeNet;
    }

    public void setSoldeNet(double soldeNet) {
        this.soldeNet = soldeNet;
    }

    public double getSoldeAvecDepot() {
        return soldeAvecDepot;
    }

    public void setSoldeAvecDepot(double soldeAvecDepot) {
        this.soldeAvecDepot = soldeAvecDepot;
    }
}
