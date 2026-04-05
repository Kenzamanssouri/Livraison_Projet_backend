package com.example.livraision_back.service;


import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.dto.DashboardVendeurResponse;
import com.example.livraision_back.model.Commande;
import com.example.livraision_back.model.StatutCommande;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommandeService {
    Page<CommandeDTO> findAll(int page, int size);
    List<CommandeDTO> findAll();


    DashboardVendeurResponse getDashboard(Long vendeurId);

    Commande creerCommandeTexteLibre(String loginClient, String texteLibre, String adresseLivraison);
    CommandeDTO findById(Long id);
    List<CommandeDTO> findByLivreur(Long livreurId);
    List<CommandeDTO> findByLivreurOrStatut(
        Long livreurId,
        StatutCommande statut
    );
}
