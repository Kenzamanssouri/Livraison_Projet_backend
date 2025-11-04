package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.UtilisateurDTO;
import com.example.livraision_back.model.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UtilisateurMapper {

    // ✅ Convertir Utilisateur → UtilisateurDTO
    public static UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;

        UtilisateurDTO dto = new UtilisateurDTO() {}; // Comme c'est abstrait
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setEmail(utilisateur.getEmail());
        dto.setLogin(utilisateur.getLogin());
        dto.setMotDePasse(utilisateur.getMotDePasse());
        dto.setTelephone(utilisateur.getTelephone());
        dto.setAdresse(utilisateur.getAdresse());
        dto.setVille(utilisateur.getVille());
        dto.setRole(utilisateur.getRole());
        dto.setResetCode(utilisateur.getResetCode());
        dto.setResetCodeExpiry(utilisateur.getResetCodeExpiry());
        dto.setDeviceToken(utilisateur.getDeviceToken());

        return dto;
    }

    // ✅ Convertir UtilisateurDTO → Utilisateur
    public static void updateEntity(Utilisateur utilisateur, UtilisateurDTO dto) {
        if (utilisateur == null || dto == null) return;

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setLogin(dto.getLogin());
        utilisateur.setMotDePasse(dto.getMotDePasse());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setAdresse(dto.getAdresse());
        utilisateur.setVille(dto.getVille());
        utilisateur.setRole(dto.getRole());
        utilisateur.setResetCode(dto.getResetCode());
        utilisateur.setResetCodeExpiry(dto.getResetCodeExpiry());
        utilisateur.setDeviceToken(dto.getDeviceToken());
    }
}
