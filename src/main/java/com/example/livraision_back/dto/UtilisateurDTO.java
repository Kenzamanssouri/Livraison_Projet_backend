package com.example.livraision_back.dto;

import com.example.livraision_back.model.RoleUtilisateur;
import lombok.Data;

@Data
public abstract class UtilisateurDTO {
    protected Long id;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String login;
    protected String motDePasse;
    protected String telephone;
    protected String adresse;
    protected RoleUtilisateur role;
    protected String resetCode;
    protected String ville;

    protected java.time.LocalDateTime resetCodeExpiry;
    protected String deviceToken; // 🔥 Ajout important

}

