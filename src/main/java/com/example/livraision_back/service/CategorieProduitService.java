package com.example.livraision_back.service;

import com.example.livraision_back.dto.CategorieProduitDTO;
import com.example.livraision_back.dto.ProduitDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorieProduitService {

    CategorieProduitDTO create(CategorieProduitDTO dto);

    CategorieProduitDTO update(Long id, CategorieProduitDTO dto);

    CategorieProduitDTO getById(Long id);

    List<CategorieProduitDTO> getAll();
    Page<CategorieProduitDTO> getPaged(int page, int size);

    void delete(Long id);
}
