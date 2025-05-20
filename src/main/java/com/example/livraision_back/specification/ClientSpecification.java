package com.example.livraision_back.specification;

import com.example.livraision_back.model.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasNom(String nom) {
        return (root, query, criteriaBuilder) ->
            nom == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
    }

    public static Specification<Client> hasPrenom(String prenom) {
        return (root, query, criteriaBuilder) ->
            prenom == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("prenom")), "%" + prenom.toLowerCase() + "%");
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
            email == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Client> hasLogin(String login) {
        return (root, query, criteriaBuilder) ->
            login == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("login")), "%" + login.toLowerCase() + "%");
    }

    public static Specification<Client> hasTelephone(String telephone) {
        return (root, query, criteriaBuilder) ->
            telephone == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("telephone")), "%" + telephone.toLowerCase() + "%");
    }
}
