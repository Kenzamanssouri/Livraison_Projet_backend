package com.example.livraision_back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String message;
    private Long idObject;
    private String Object;
    private Boolean opened = false; // <-- New field

}
