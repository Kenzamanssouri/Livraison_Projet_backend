package com.example.livraision_back.service;

import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Vendeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VendeurService {
    Vendeur save(Vendeur dto);
    List<Vendeur> findAll();
    Page<Vendeur> findAll(Pageable pageable);

    Vendeur findById(Long id);
    Vendeur update(Long id, VendeurDTO dto);
    boolean delete(Long id);

    Page<Vendeur> searchVendeurs(VendeurDTO filter, Pageable pageable);

     boolean existsByEmail(String email);
}
