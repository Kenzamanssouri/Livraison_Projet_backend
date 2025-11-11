package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.HoraireDTO;
import com.example.livraision_back.model.Horaire;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HoraireMapper {
    @Named("toDTO") // Add this annotation
    private HoraireDTO toHoraireDTO(Horaire horaire) {
        if (horaire == null) return null;
        HoraireDTO dto = new HoraireDTO();
        dto.setJour(horaire.getJour());
        dto.setHeureOuverture(horaire.getHeureOuverture());
        dto.setHeureFermeture(horaire.getHeureFermeture());
        return dto;
    }

    private Horaire toHoraireEntity(HoraireDTO dto) {
        if (dto == null) return null;
        Horaire h = new Horaire();
        h.setJour(dto.getJour());
        h.setHeureOuverture(dto.getHeureOuverture());
        h.setHeureFermeture(dto.getHeureFermeture());
        return h;
    }

}
