package com.example.livraision_back.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login;
    private String email;
    private String motDePasse;
    private int role;
}
