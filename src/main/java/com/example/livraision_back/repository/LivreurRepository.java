package com.example.livraision_back.repository;

import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.Vendeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface LivreurRepository extends JpaRepository<Livreur, Long> {
    Optional<Livreur> findByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

    List<Livreur> findAllByEstValideParAdminIsNullOrEstValideParAdminFalse();


}
