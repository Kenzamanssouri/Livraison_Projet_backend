package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.ProduitDTO;
import com.example.livraision_back.model.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO toDTO(Produit entity);
    Produit toEntity(ProduitDTO dto);
}
