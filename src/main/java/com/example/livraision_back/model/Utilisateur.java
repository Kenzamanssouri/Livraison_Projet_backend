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
    @Column(unique = true) // 👈 make email unique

    protected String email;
    @Column(unique = true) // 👈 make login unique

    protected String login;
    protected String motDePasse;
    protected String telephone;
    protected String adresse;
    protected String ville;
    protected RoleUtilisateur role; // CLIENT, VENDEUR, LIVREUR,ADMIN
    protected String resetCode;

    protected java.time.LocalDateTime resetCodeExpiry;

    // Getters and Setters
}
