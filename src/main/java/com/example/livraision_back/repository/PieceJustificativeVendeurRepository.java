package com.example.livraision_back.repository;

import com.example.livraision_back.model.PieceJustificativeVendeur;
import com.example.livraision_back.model.Vendeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface PieceJustificativeVendeurRepository extends JpaRepository<PieceJustificativeVendeur, Long>, JpaSpecificationExecutor<PieceJustificativeVendeur> {
}
