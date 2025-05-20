package com.example.livraision_back.dto;

import com.example.livraision_back.model.GeoLocalisation;
import lombok.Data;

@Data
public class AdresseDTO {
    private String rue;
    private String ville;
    private String codePostal;

    private GeoLocalisationDTO localisation;
}
