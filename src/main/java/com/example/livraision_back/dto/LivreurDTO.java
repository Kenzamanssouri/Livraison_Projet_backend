package com.example.livraision_back.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class LivreurDTO extends UtilisateurDTO {
    private boolean disponible;
    private GeoLocalisationDTO positionActuelle;
    private List<Long> commandeIds;
    private double commissionTotale;
    private double encaissementsTotaux;
    private LocalDate dateDernierEncaissement;
    private boolean bloque;
    private double soldeNet;
}
