package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.HoraireDTO;
import com.example.livraision_back.model.Horaire;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HoraireMapper {
    @Named("toDTO") // Add this annotation
    HoraireDTO toDTO(Horaire horaire);
    Horaire toEntity(HoraireDTO dto);
}
