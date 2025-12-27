package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.ProduitDTO;
import com.example.livraision_back.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitDTO toDto(Produit entity);

    @Mapping(target = "vendeur", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "options", ignore = true)
    Produit toEntity(ProduitDTO dto);
}
