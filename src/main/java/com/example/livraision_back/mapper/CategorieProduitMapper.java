package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.CategorieProduitDTO;
import com.example.livraision_back.model.CategorieProduit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategorieProduitMapper {

    CategorieProduitDTO toDto(CategorieProduit entity);

    CategorieProduit toEntity(CategorieProduitDTO dto);

    List<CategorieProduitDTO> toDtoList(List<CategorieProduit> entities);

    List<CategorieProduit> toEntityList(List<CategorieProduitDTO> dtos);
}
