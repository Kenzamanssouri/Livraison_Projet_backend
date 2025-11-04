package com.example.livraision_back.repository;



import com.example.livraision_back.model.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    List<PieceJointe> findByIdObjetAndTypePj(Long id, String typePj);

    List<PieceJointe> findByTypePj(String typePj);
}

