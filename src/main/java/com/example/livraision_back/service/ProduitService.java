package com.example.livraision_back.service;

import com.example.livraision_back.dto.ProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProduitService {

    ProduitDTO addProduit(ProduitDTO dto);

    Page<ProduitDTO> getPaged(Long vendeurId, String search, int page, int size);

    ProduitDTO getById(Long id);

    ProduitDTO update(Long id, ProduitDTO dto);

    void delete(Long id);

    boolean toggle(Long id);
}
