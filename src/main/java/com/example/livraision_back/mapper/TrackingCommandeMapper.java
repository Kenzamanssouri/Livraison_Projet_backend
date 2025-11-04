package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.TrackingCommandeDTO;
import com.example.livraision_back.model.TrackingCommande;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {EvenementTrackingMapper.class})
public interface TrackingCommandeMapper {
    TrackingCommandeMapper INSTANCE = Mappers.getMapper(TrackingCommandeMapper.class);

    TrackingCommandeDTO toDTO(TrackingCommande tracking);
    TrackingCommande toEntity(TrackingCommandeDTO dto);
}
