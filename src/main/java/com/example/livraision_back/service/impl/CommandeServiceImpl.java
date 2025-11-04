package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.mapper.CommandeMapper;
import com.example.livraision_back.model.Commande;
import com.example.livraision_back.repository.CommandeRepository;
import com.example.livraision_back.service.CommandeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;

    public CommandeServiceImpl(CommandeRepository commandeRepository, CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
    }

    @Override
    public Page<CommandeDTO> findAll(int page, int size) {
        // Création du PageRequest
        PageRequest pageRequest = PageRequest.of(page, size);

        // Récupération paginée depuis le repository
        Page<Commande> commandes = commandeRepository.findAllWithLignesAndProduits(pageRequest);

        // Conversion en DTO
        Page<CommandeDTO> dtoPage = commandes.map(commandeMapper::toDTO);

        return dtoPage;
    }


    @Override
    public List<CommandeDTO> findAll() {
        List<Commande> commandes = commandeRepository.findAll();

        // Conversion en DTO
        List<CommandeDTO> dtoList = commandes.stream()
            .map(commandeMapper::toDTO)
            .collect(Collectors.toList());

        return dtoList;
    }


}
