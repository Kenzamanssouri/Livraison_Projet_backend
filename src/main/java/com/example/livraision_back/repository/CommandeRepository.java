package com.example.livraision_back.repository;

import com.example.livraision_back.model.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>, JpaSpecificationExecutor<Commande> {
    @Query(
        value = "SELECT DISTINCT c FROM Commande c " +
            "LEFT JOIN FETCH c.lignes l " +
            "LEFT JOIN FETCH l.produit",
        countQuery = "SELECT COUNT(c) FROM Commande c"
    )
    Page<Commande> findAllWithLignesAndProduits(Pageable pageable);

}
