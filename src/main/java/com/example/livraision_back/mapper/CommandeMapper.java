package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.model.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {
        LigneCommandeMapper.class,
        ClientMapper.class,
        LivreurMapper.class
    }
)
public interface CommandeMapper {

    // 🔹 Mapping COMPLET
    @Named("full")
    @Mapping(target = "livraisonAdresse", source = "livraisonAdresse")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "vendeur", source = "vendeur")
    @Mapping(target = "livreur", source = "livreur")
    @Mapping(target = "lignes", source = "lignes")
    @Mapping(target = "tracking", source = "tracking")
    CommandeDTO toDTO(Commande commande);

    // 🔹 Mapping LÉGER LIVREUR
    @Named("livreur")
    @Mapping(target = "livraisonAdresse", source = "livraisonAdresse")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vendeur", ignore = true)
    @Mapping(target = "livreur", ignore = true)
    @Mapping(target = "lignes", ignore = true)
    @Mapping(target = "tracking", ignore = true)
    CommandeDTO toLivreurDTO(Commande commande);

    // 🔹 Liste → DTO COMPLET
    @IterableMapping(qualifiedByName = "full")
    List<CommandeDTO> toDTOs(List<Commande> commandes);

    // 🔹 Mapping inverse
    @Mapping(target = "livraisonAdresse", source = "livraisonAdresse")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "vendeur", source = "vendeur")
    @Mapping(target = "livreur", source = "livreur")
    @Mapping(target = "lignes", source = "lignes")
    @Mapping(target = "tracking", source = "tracking")
    Commande toEntity(CommandeDTO dto);
}
