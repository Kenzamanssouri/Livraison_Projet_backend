package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String url;
    private Long size;
    private String type;


    private Long idObjet;

    private String typePj;
}
