package com.example.livraision_back.repository;

import com.example.livraision_back.model.ParametresPlateforme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametresRepository extends JpaRepository<ParametresPlateforme, Long> {
}
