package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.LigneCommandeDTO;
import com.example.livraision_back.model.LigneCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface LigneCommandeMapper {
    @Mapping(target = "descriptionLibre", source = "descriptionLibre")

    LigneCommandeDTO toDTO(LigneCommande entity);
    @Mapping(target = "descriptionLibre", source = "descriptionLibre")

    LigneCommande toEntity(LigneCommandeDTO dto);
}
