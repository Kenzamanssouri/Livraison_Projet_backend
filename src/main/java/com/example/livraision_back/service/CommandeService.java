package com.example.livraision_back.service;


import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.model.Commande;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommandeService {
    Page<CommandeDTO> findAll(int page, int size);
    List<CommandeDTO> findAll();

}
