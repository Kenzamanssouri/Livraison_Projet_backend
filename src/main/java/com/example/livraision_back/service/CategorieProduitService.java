package com.example.livraision_back.service;

import com.example.livraision_back.dto.CategorieProduitDTO;

import java.util.List;

public interface CategorieProduitService {

    CategorieProduitDTO create(CategorieProduitDTO dto);

    CategorieProduitDTO update(Long id, CategorieProduitDTO dto);

    CategorieProduitDTO getById(Long id);

    List<CategorieProduitDTO> getAll();

    void delete(Long id);
}
