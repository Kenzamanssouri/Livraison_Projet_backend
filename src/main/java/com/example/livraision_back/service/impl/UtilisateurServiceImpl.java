package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.UtilisateurDTO;
import com.example.livraision_back.mapper.UtilisateurMapper;
import com.example.livraision_back.model.Commande;
import com.example.livraision_back.model.Utilisateur;
import com.example.livraision_back.repository.UtilisateurRepository;
import com.example.livraision_back.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    public List<UtilisateurDTO> findAll() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
            .map(UtilisateurMapper::toDTO) // si c'est une méthode statique
            .collect(Collectors.toList());
    }

}
