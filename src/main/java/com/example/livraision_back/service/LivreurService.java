package com.example.livraision_back.service;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Vendeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LivreurService {

    LivreurDTO save(LivreurDTO dto);
    List<LivreurDTO> findAll();
    Page<LivreurDTO> findAll(Pageable pageable);

    LivreurDTO findById(Long id);
    LivreurDTO update(Long id, LivreurDTO dto);
    boolean delete(Long id);

    boolean existsByEmail(String email);
}
