package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.EvenementTrackingDTO;
import com.example.livraision_back.model.EvenementTracking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EvenementTrackingMapper {
    EvenementTrackingMapper INSTANCE = Mappers.getMapper(EvenementTrackingMapper.class);

    EvenementTrackingDTO toDTO(EvenementTracking evenement);
    EvenementTracking toEntity(EvenementTrackingDTO dto);
}
