package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.GeoLocalisationDTO;
import com.example.livraision_back.model.GeoLocalisation;

public interface GeoLocalisationMapper {

    public static GeoLocalisationDTO toDTO(GeoLocalisation entity) {
        if (entity == null) return null;
        GeoLocalisationDTO dto = new GeoLocalisationDTO();
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }

    public static GeoLocalisation toEntity(GeoLocalisationDTO dto) {
        if (dto == null) return null;
        GeoLocalisation entity = new GeoLocalisation();
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }
}
