package com.example.livraision_back.repository;

import com.example.livraision_back.model.Vendeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendeurRepository extends JpaRepository<Vendeur, Long>, JpaSpecificationExecutor<Vendeur> {
    Optional<Vendeur> findByLogin(String login);

    List<Vendeur> findAllByEstValideParAdminIsNull();

    Optional<Vendeur> findByEmail(String email);

    Optional<Vendeur> findByTelephone(String telephone);
    boolean existsByEmail(String email);

}
