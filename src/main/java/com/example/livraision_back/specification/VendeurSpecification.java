package com.example.livraision_back.specification;

import com.example.livraision_back.model.Vendeur;
import org.springframework.data.jpa.domain.Specification;

public class VendeurSpecification {

    public static Specification<Vendeur> hasNom(String nom) {
        return (root, query, cb) ->
            nom == null ? null : cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasPrenom(String prenom) {
        return (root, query, cb) ->
            prenom == null ? null : cb.like(cb.lower(root.get("prenom")), "%" + prenom.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasEmail(String email) {
        return (root, query, cb) ->
            email == null ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasLogin(String login) {
        return (root, query, cb) ->
            login == null ? null : cb.like(cb.lower(root.get("login")), "%" + login.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasTelephone(String telephone) {
        return (root, query, cb) ->
            telephone == null ? null : cb.like(cb.lower(root.get("telephone")), "%" + telephone.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasAdresse(String adresse) {
        return (root, query, cb) ->
            adresse == null ? null : cb.like(cb.lower(root.get("adresse")), "%" + adresse.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasVille(String ville) {
        return (root, query, cb) ->
            ville == null ? null : cb.like(cb.lower(root.get("ville")), "%" + ville.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasNomEtablissement(String nomEtablissement) {
        return (root, query, cb) ->
            nomEtablissement == null ? null : cb.like(cb.lower(root.get("nomEtablissement")), "%" + nomEtablissement.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasCategorie(String categorie) {
        return (root, query, cb) ->
            categorie == null ? null : cb.like(cb.lower(root.get("categorie")), "%" + categorie.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasRegistreCommerce(String registreCommerce) {
        return (root, query, cb) ->
            registreCommerce == null ? null : cb.like(cb.lower(root.get("registreCommerce")), "%" + registreCommerce.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasIdentifiantFiscal(String identifiantFiscal) {
        return (root, query, cb) ->
            identifiantFiscal == null ? null : cb.like(cb.lower(root.get("identifiantFiscal")), "%" + identifiantFiscal.toLowerCase() + "%");
    }

    public static Specification<Vendeur> hasRib(String rib) {
        return (root, query, cb) ->
            rib == null ? null : cb.like(cb.lower(root.get("rib")), "%" + rib.toLowerCase() + "%");
    }

    public static Specification<Vendeur> isValideParAdmin(Boolean estValideParAdmin) {
        return (root, query, cb) ->
            estValideParAdmin == null ? null : cb.equal(root.get("estValideParAdmin"), estValideParAdmin);
    }

    public static Specification<Vendeur> hasRole(com.example.livraision_back.model.RoleUtilisateur role) {
        return (root, query, cb) ->
            role == null ? null : cb.equal(root.get("role"), role);
    }

}
