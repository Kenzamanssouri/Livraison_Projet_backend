package com.example.livraision_back.repository;

import com.example.livraision_back.model.Commande;
import com.example.livraision_back.model.StatutCommande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>, JpaSpecificationExecutor<Commande> {
    @Query(
        value = "SELECT DISTINCT c FROM Commande c " +
            "LEFT JOIN FETCH c.lignes l " +
            "LEFT JOIN FETCH l.produit",
        countQuery = "SELECT COUNT(c) FROM Commande c"
    )
    Page<Commande> findAllWithLignesAndProduits(Pageable pageable);

    // Total commandes pour un vendeur
    Long countByVendeur_Id(Long vendeurId);

    // Commandes dans une plage de dates
    Long countByVendeur_IdAndDateCommandeBetween(Long vendeurId,
                                                 LocalDateTime start,
                                                 LocalDateTime end);

    // Commandes par statut
    Long countByVendeur_IdAndStatut(Long vendeurId, StatutCommande statut);

    // Revenus totaux
    @Query("""
           SELECT COALESCE(SUM(c.total), 0)
           FROM Commande c
           WHERE c.vendeur.id = :vendeurId
           """)
    Double sumTotalByVendeur(@Param("vendeurId") Long vendeurId);

    // Revenus dans une plage de dates
    @Query("""
           SELECT COALESCE(SUM(c.total), 0)
           FROM Commande c
           WHERE c.vendeur.id = :vendeurId
             AND c.dateCommande BETWEEN :start AND :end
           """)
    Double sumTotalByVendeurAndDateBetween(@Param("vendeurId") Long vendeurId,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    // Panier moyen global
    @Query("""
           SELECT COALESCE(AVG(c.total), 0)
           FROM Commande c
           WHERE c.vendeur.id = :vendeurId
           """)
    Double avgTotalByVendeur(@Param("vendeurId") Long vendeurId);

    // Nombre commandes par mode de paiement
    Long countByVendeur_IdAndModePaiement(Long vendeurId, String modePaiement);

}
