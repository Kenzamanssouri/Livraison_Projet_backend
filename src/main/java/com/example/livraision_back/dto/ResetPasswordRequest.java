package com.example.livraision_back.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String telephone;
    private String code;
    private String nouveauMotDePasse;
}
