package com.example.livraision_back.dto;

import com.example.livraision_back.model.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeDTO {
    private Long id;

    private ClientDTO client;
    private VendeurDTO vendeur;
    private LivreurDTO livreur;
    private LocalDateTime dateCommande;
    private LocalDateTime dateLivraisonEstimee;
    private StatutCommande statut;
    private List<LigneCommandeDTO> lignes;
    private String livraisonAdresse;
    private TrackingCommandeDTO tracking;
    private double total;
    private String modePaiement;

}
