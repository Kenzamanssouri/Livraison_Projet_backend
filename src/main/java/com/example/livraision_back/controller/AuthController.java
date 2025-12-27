package com.example.livraision_back.controller;

import com.example.livraision_back.dto.AuthenticationRequest;
import com.example.livraision_back.dto.AuthenticationResponse;
import com.example.livraision_back.dto.ResetPasswordRequest;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.Utilisateur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.AdminRepository;
import com.example.livraision_back.repository.ClientRepository;
import com.example.livraision_back.repository.LivreurRepository;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.security.JwtUtil;
import com.example.livraision_back.service.AdminAuthenticationProvider;
import com.example.livraision_back.service.ClientAuthenticationProvider;
import com.example.livraision_back.service.PasswordResetService;
import com.example.livraision_back.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private ClientRepository clientRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private VendeurRepository vendeurRepository;
    @Autowired private LivreurRepository livreurRepository;
    @Autowired
    private AdminRepository adminRepository;
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
            // 🔍 1️⃣ Rechercher l'utilisateur par login
            Utilisateur user = findUserByLogin(request.getLogin());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Aucun utilisateur trouvé avec ce login");
            }
            String mdpReq=request.getMotDePasse();
            String mdpUser=user.getMotDePasse();
            Boolean test=passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse());

            // 🔑 2️⃣ Vérifier le mot de passe
            if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
                throw new BadCredentialsException("Mot de passe incorrect");
            }
            // 🧩 3️⃣ Vérifier les conditions selon le type d'utilisateur
            if (user instanceof Vendeur) {
                Vendeur vendeur = (Vendeur) user;
                if (!Boolean.TRUE.equals(vendeur.getEstValideParAdmin())) {
                    // false ou null

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Votre compte vendeur n’a pas encore été validé par l’administrateur.");
                }
            }

            if (user instanceof Livreur) {
                Livreur livreur = (Livreur) user;
                if (!Boolean.TRUE.equals(livreur.getEstValideParAdmin())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Votre compte livreur n’a pas encore été validé par l’administrateur.");
                }
            }

            // ✅ 4️⃣ Générer le token JWT
            String jwt = jwtUtil.generateToken(user.getLogin());

            // ✅ 5️⃣ Retourner la réponse
            return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getRole().name(), user.getId()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }



    /**
     * 🔍 Recherche l'utilisateur dans chaque table.
     */
    private Utilisateur findUserByLogin(String login) {
        if (adminRepository.findByLogin(login).isPresent())
            return adminRepository.findByLogin(login).get();
        if (clientRepository.findByLogin(login).isPresent())
            return clientRepository.findByLogin(login).get();
        if (vendeurRepository.findByLogin(login).isPresent())
            return vendeurRepository.findByLogin(login).get();
        if (livreurRepository.findByLogin(login).isPresent())
            return livreurRepository.findByLogin(login).get();
        return null;
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
