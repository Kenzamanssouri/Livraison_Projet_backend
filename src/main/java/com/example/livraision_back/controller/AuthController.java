package com.example.livraision_back.controller;

import com.example.livraision_back.dto.AuthenticationRequest;
import com.example.livraision_back.dto.AuthenticationResponse;
import com.example.livraision_back.dto.ResetPasswordRequest;
import com.example.livraision_back.security.JwtUtil;
import com.example.livraision_back.service.AdminAuthenticationProvider;
import com.example.livraision_back.service.ClientAuthenticationProvider;
import com.example.livraision_back.service.PasswordResetService;
import com.example.livraision_back.service.impl.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordResetService passwordResetService;
    private final AdminAuthenticationProvider adminAuthenticationProvider;
    private final ClientAuthenticationProvider clientAuthenticationProvider;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, PasswordResetService passwordResetService, AdminAuthenticationProvider adminAuthenticationProvider, ClientAuthenticationProvider clientAuthenticationProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordResetService = passwordResetService;
        this.adminAuthenticationProvider = adminAuthenticationProvider;
        this.clientAuthenticationProvider = clientAuthenticationProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            // Sélectionner le AuthenticationProvider en fonction du rôle
            Authentication authentication;

            if (request.getRole() == 0) {  // Client
                authentication = clientAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
                );
            } else if (request.getRole() == 3) {  // Admin
                authentication = adminAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rôle inconnu");
            }

            // Si l'authentification est réussie, on charge les détails de l'utilisateur
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Générer un token JWT
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Retourner la réponse avec le token
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }




    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean success = passwordResetService.resetPassword(request.getTelephone(), request.getCode(), request.getNouveauMotDePasse());

        if (success) {
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } else {
            return ResponseEntity.badRequest().body("Code invalide ou expiré.");
        }
    }

}
