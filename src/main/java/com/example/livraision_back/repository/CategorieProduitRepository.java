package com.example.livraision_back.repository;

import com.example.livraision_back.model.CategorieProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long> {

    Optional<CategorieProduit> findByNom(String nom);

    boolean existsByNom(String nom);
}
