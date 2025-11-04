package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.model.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
    LigneCommandeMapper.class,
    ClientMapper.class,
    LivreurMapper.class})
public interface CommandeMapper {

    @Mapping(target = "client", source = "client")
    @Mapping(target = "vendeur", source = "vendeur")
    @Mapping(target = "livreur", source = "livreur")
    @Mapping(target = "lignes", source = "lignes")
    @Mapping(target = "tracking", source = "tracking")
    CommandeDTO toDTO(Commande commande);

    @Mapping(target = "client", source = "client")
    @Mapping(target = "vendeur", source = "vendeur")
    @Mapping(target = "livreur", source = "livreur")
    @Mapping(target = "lignes", source = "lignes")
    @Mapping(target = "tracking", source = "tracking")
    Commande toEntity(CommandeDTO dto);

    List<CommandeDTO> toDTOs(List<Commande> commandes);
}
