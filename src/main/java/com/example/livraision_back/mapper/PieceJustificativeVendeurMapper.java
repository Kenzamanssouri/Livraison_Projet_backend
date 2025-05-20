package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.ClientDTO;
import com.example.livraision_back.dto.PieceJustificativeVendeurDTO;
import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Horaire;
import com.example.livraision_back.model.PieceJustificativeVendeur;
import com.example.livraision_back.model.Vendeur;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")

public interface PieceJustificativeVendeurMapper {
    PieceJustificativeVendeurDTO toDTO(PieceJustificativeVendeur client);

    default PieceJustificativeVendeur toEntity(PieceJustificativeVendeurDTO dto) {
        if (dto == null) {
            return null;
        }

        PieceJustificativeVendeur pieceJustificativeVendeur = new PieceJustificativeVendeur();
        pieceJustificativeVendeur.setTypeFichier(dto.getTypeFichier());
        pieceJustificativeVendeur.setId(dto.getId());
        pieceJustificativeVendeur.setNomFichier(dto.getNomFichier());
        pieceJustificativeVendeur.setCheminFichier(dto.getCheminFichier());

        if (dto.getVendeur() != null) {
            pieceJustificativeVendeur.setVendeur(VendeurFromDTO(dto.getVendeur()));
        }

        return pieceJustificativeVendeur;
    }

    Vendeur VendeurFromDTO(VendeurDTO dto);

}
