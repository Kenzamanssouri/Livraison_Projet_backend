package com.example.livraision_back.dto;

import lombok.Data;

import java.util.List;

@Data
public class VendeurDTO extends UtilisateurDTO {
    private String nomEtablissement;
    private String categorie;
    private String registreCommerce;
    private String identifiantFiscal;
    private String rib;
    private boolean estValideParAdmin;
    private String motifRejet;

    private HoraireDTO horaireOuverture;
    private List<Long> produitIds;
    private List<Long> commandeIds;

}
