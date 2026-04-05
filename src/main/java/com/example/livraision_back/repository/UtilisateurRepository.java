package com.example.livraision_back.repository;

import com.example.livraision_back.model.PieceJointe;
import com.example.livraision_back.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
