package com.example.livraision_back.dto;

import lombok.Data;
import java.util.List;

@Data
public class VendeurUpdateDTO {

    private String nom;
    private String prenom;
    private String login;
    private String telephone;
    private String adresse;
    private String nomEtablissement;

    // 🔐 optionnel
    private String motDePasse;

    // 🕒 AJOUT : horaires d'ouverture
    private List<HoraireDTO> horairesOuverture;
}
