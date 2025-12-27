package com.example.livraision_back.repository;



import com.example.livraision_back.model.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {

    @Query(value = """
    SELECT *
    FROM piece_jointe
    WHERE id_objet = :idObjet
    AND type_pj LIKE CONCAT(:prefix, '%')
""", nativeQuery = true)
    List<PieceJointe> findByIdObjetAndTypePjStartingWith(
        @Param("idObjet") Long idObjet,
        @Param("prefix") String typePjPrefix
    );


    List<PieceJointe> findByTypePj(String typePj);
}

