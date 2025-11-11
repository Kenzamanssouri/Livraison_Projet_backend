package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.model.Livreur;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LivreurMapper {

    static LivreurDTO toDTO(Livreur livreur) {
        if (livreur == null) return null;

        LivreurDTO dto = new LivreurDTO();

        // Héritage de UtilisateurDTO
        dto.setId(livreur.getId());
        dto.setNom(livreur.getNom());
        dto.setPrenom(livreur.getPrenom());
        dto.setEmail(livreur.getEmail());
        dto.setTelephone(livreur.getTelephone());
        dto.setAdresse(livreur.getAdresse());
        dto.setVille(livreur.getVille());
        dto.setRole(livreur.getRole());
        dto.setLogin(livreur.getLogin());
        dto.setMotDePasse(livreur.getMotDePasse());

        // Champs spécifiques à Livreur
        dto.setDisponible(livreur.isDisponible());
        dto.setPositionActuelle(GeoLocalisationMapper.toDTO(livreur.getPositionActuelle()));
        dto.setCommissionTotale(livreur.getCommissionTotale());
        dto.setEncaissementsTotaux(livreur.getEncaissementsTotaux());
        dto.setDateDernierEncaissement(livreur.getDateDernierEncaissement());
        dto.setBloque(livreur.isBloque());
        dto.setEstValideParAdmin(livreur.getEstValideParAdmin());
        dto.setMotifRejet(livreur.getMotifRejet());
        dto.setDepotGarantie(livreur.getDepotGarantie());
        dto.setSoldeNet(livreur.getSoldeNet());
        dto.setSoldeAvecDepot(livreur.getSoldeAvecDepot());

        // Mapper les commandes (juste les IDs)
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

    static Livreur toEntity(LivreurDTO dto) {
        if (dto == null) return null;

        Livreur livreur = new Livreur();

        // Héritage de Utilisateur
        livreur.setId(dto.getId());
        livreur.setNom(dto.getNom());
        livreur.setPrenom(dto.getPrenom());
        livreur.setEmail(dto.getEmail());
        livreur.setTelephone(dto.getTelephone());
        livreur.setAdresse(dto.getAdresse());
        livreur.setVille(dto.getVille());
        livreur.setRole(dto.getRole());
        livreur.setLogin(dto.getLogin());
        livreur.setMotDePasse(dto.getMotDePasse());

        // Champs spécifiques à Livreur
        livreur.setDisponible(dto.isDisponible());
        livreur.setPositionActuelle(GeoLocalisationMapper.toEntity(dto.getPositionActuelle()));
        livreur.setCommissionTotale(dto.getCommissionTotale());
        livreur.setEncaissementsTotaux(dto.getEncaissementsTotaux());
        livreur.setDateDernierEncaissement(dto.getDateDernierEncaissement());
        livreur.setBloque(dto.isBloque());
        livreur.setEstValideParAdmin(dto.getEstValideParAdmin());
        livreur.setMotifRejet(dto.getMotifRejet());
        livreur.setDepotGarantie(dto.getDepotGarantie());

        // Commandes : généralement gérées séparément dans le service
        // livreur.setCommandesLivrees(...);

        return livreur;
    }
}
