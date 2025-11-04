package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nom;
    protected String prenom;

    @Column(unique = true)
    protected String email;

    @Column(unique = true)
    protected String login;

    protected String motDePasse;
    protected String telephone;
    protected String adresse;
    protected String ville;
    protected RoleUtilisateur role;
    protected String resetCode;
    protected java.time.LocalDateTime resetCodeExpiry;

    // ✅ Champ pour notifications push Firebase
    protected String deviceToken; // 🔥 Ajout important
}
