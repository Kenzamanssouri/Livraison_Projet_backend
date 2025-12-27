package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Vendeur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendeurMapper {
    VendeurDTO toDto(Vendeur vendeur);
}
