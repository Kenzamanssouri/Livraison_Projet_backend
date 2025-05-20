package com.example.livraision_back.dto;

import com.example.livraision_back.model.Commande;
import lombok.Data;

import java.util.List;
@Data
public class ClientDTO extends UtilisateurDTO {
    // Tu peux inclure les IDs de commandes ou les résumer
    private List<Long> commandeIds;
}

