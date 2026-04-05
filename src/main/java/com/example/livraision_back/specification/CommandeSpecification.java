package com.example.livraision_back.specification;

import com.example.livraision_back.model.Commande;
import com.example.livraision_back.model.StatutCommande;
import org.springframework.data.jpa.domain.Specification;

public class CommandeSpecification {

    public static Specification<Commande> hasLivreur(Long livreurId) {
        return (root, query, cb) ->
            cb.equal(root.get("livreur").get("id"), livreurId);
    }

    public static Specification<Commande> hasStatut(StatutCommande statut) {
        return (root, query, cb) ->
            cb.equal(root.get("statut"), statut);
    }

    public static Specification<Commande> livreurOrStatut(
        Long livreurId,
        StatutCommande statut
    ) {
        return (root, query, cb) -> cb.or(
            livreurId != null
                ? cb.equal(root.get("livreur").get("id"), livreurId)
                : cb.conjunction(),

            statut != null
                ? cb.equal(root.get("statut"), statut)
                : cb.conjunction()
        );
    }
}
