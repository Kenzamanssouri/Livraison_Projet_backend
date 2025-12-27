package com.example.livraision_back.repository;

import com.example.livraision_back.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Page<Produit> findByVendeurId(Long vendeurId, Pageable pageable);

    Page<Produit> findByVendeurIdAndNomContainingIgnoreCase(
        Long vendeurId, String nom, Pageable pageable
    );
}
