package com.example.livraision_back.repository;

import com.example.livraision_back.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Optional<Client> findByLogin(String login);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByTelephone(String telephone);
    boolean existsByEmail(String email);

}
