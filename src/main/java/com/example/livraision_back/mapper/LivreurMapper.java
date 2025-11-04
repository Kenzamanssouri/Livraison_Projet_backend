package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.model.Livreur;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;
@Mapper(componentModel = "spring")

public interface LivreurMapper {

    public static LivreurDTO toDTO(Livreur livreur) {
        if (livreur == null) return null;

        LivreurDTO dto = new LivreurDTO();

        // Héritage de UtilisateurDTO
        dto.setId(livreur.getId());
        dto.setNom(livreur.getNom());
        dto.setPrenom(livreur.getPrenom());
        dto.setEmail(livreur.getEmail());
        dto.setTelephone(livreur.getTelephone());
        dto.setDepotGarantie(livreur.getDepotGarantie());
        dto.setRole(livreur.getRole());
        dto.setAdresse(livreur.getAdresse());
        // ajoute les autres champs de Utilisateur ici

        dto.setDisponible(livreur.isDisponible());
        dto.setPositionActuelle(GeoLocalisationMapper.toDTO(livreur.getPositionActuelle()));
        dto.setCommissionTotale(livreur.getCommissionTotale());
        dto.setEncaissementsTotaux(livreur.getEncaissementsTotaux());
        dto.setDateDernierEncaissement(livreur.getDateDernierEncaissement());
        dto.setBloque(livreur.isBloque());
        dto.setEstValideParAdmin(livreur.getEstValideParAdmin());
        dto.setSoldeNet(livreur.getSoldeNet());
        dto.setLogin(livreur.getLogin());
        dto.setMotifRejet(livreur.getMotifRejet());

        // Mapper les commandes (juste les ids)
        if (livreur.getCommandesLivrees() != null) {
            dto.setCommandeIds(
                livreur.getCommandesLivrees()
                    .stream()
                    .map(c -> c.getId())
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Livreur toEntity(LivreurDTO dto) {
        if (dto == null) return null;

        Livreur livreur = new Livreur();

        // Héritage de Utilisateur
        livreur.setId(dto.getId());
        livreur.setNom(dto.getNom());
        livreur.setPrenom(dto.getPrenom());
        livreur.setEmail(dto.getEmail());
        livreur.setTelephone(dto.getTelephone());
        livreur.setDepotGarantie(dto.getDepotGarantie());
        livreur.setRole(dto.getRole());
        livreur.setAdresse(dto.getAdresse());
        livreur.setMotDePasse(dto.getMotDePasse());
        // ajoute les autres champs de Utilisateur ici

        livreur.setDisponible(dto.isDisponible());
        livreur.setPositionActuelle(GeoLocalisationMapper.toEntity(dto.getPositionActuelle()));
        livreur.setCommissionTotale(dto.getCommissionTotale());
        livreur.setEncaissementsTotaux(dto.getEncaissementsTotaux());
        livreur.setDateDernierEncaissement(dto.getDateDernierEncaissement());
        livreur.setBloque(dto.isBloque());
        livreur.setEstValideParAdmin(dto.getEstValideParAdmin());
        livreur.setLogin(dto.getLogin());
        livreur.setMotifRejet(dto.getMotifRejet());
        // Commandes : généralement gérées séparément dans le service

        return livreur;
    }
}
