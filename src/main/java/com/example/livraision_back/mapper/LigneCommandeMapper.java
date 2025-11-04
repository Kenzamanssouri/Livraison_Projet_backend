package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.LigneCommandeDTO;
import com.example.livraision_back.model.LigneCommande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface LigneCommandeMapper {
    LigneCommandeDTO toDTO(LigneCommande entity);
    LigneCommande toEntity(LigneCommandeDTO dto);
}
