package com.example.livraision_back.controller;

import com.example.livraision_back.dto.*;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.RoleUtilisateur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.*;
import com.example.livraision_back.service.UtilisateurService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UtilisateurController {

    private final VendeurRepository vendeurRepository;
    private final UtilisateurService userService;
    private final LivreurRepository livreurRepository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    public UtilisateurController(
        VendeurRepository vendeurRepository,
        UtilisateurService userService,
        LivreurRepository livreurRepository,
        AdminRepository adminRepository,
        ClientRepository clientRepository) {
        this.vendeurRepository = vendeurRepository;
        this.userService = userService;
        this.livreurRepository = livreurRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    // ✅ Récupère tous les utilisateurs avec pagination, recherche et filtrage par rôle
    @GetMapping
    public ResponseEntity<Page<UtilisateurDTO>> getUtilisateursPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String role) {

        List<UtilisateurDTO> utilisateurs = new ArrayList<>();

        // 🔹 1. Mapper les vendeurs
        vendeurRepository.findAll().forEach(vendeur -> {
            VendeurDTO dto = new VendeurDTO();
            dto.setId(vendeur.getId());
            dto.setNom(vendeur.getNom());
            dto.setPrenom(vendeur.getPrenom());
            dto.setEmail(vendeur.getEmail());
            dto.setLogin(vendeur.getLogin());
            dto.setMotDePasse(vendeur.getMotDePasse());
            dto.setTelephone(vendeur.getTelephone());
            dto.setAdresse(vendeur.getAdresse());
            dto.setVille(vendeur.getVille());
            dto.setRole(vendeur.getRole());
            dto.setResetCode(vendeur.getResetCode());
            dto.setResetCodeExpiry(vendeur.getResetCodeExpiry());
            dto.setDeviceToken(vendeur.getDeviceToken());
            dto.setBloque(vendeur.getBloque());
            dto.setMotifRejet(vendeur.getMotifRejet());

            // Champs spécifiques vendeur
            dto.setNomEtablissement(vendeur.getNomEtablissement());
            dto.setCategorie(vendeur.getCategorie() != null ? vendeur.getCategorie().name() : null);

            dto.setRegistreCommerce(vendeur.getRegistreCommerce());
            dto.setIdentifiantFiscal(vendeur.getIdentifiantFiscal());
            dto.setRib(vendeur.getRib());
            dto.setEstValideParAdmin(vendeur.getEstValideParAdmin());

            if (vendeur.getProduits() != null)
                dto.setProduitIds(vendeur.getProduits().stream().map(p -> p.getId()).toList());
            if (vendeur.getCommandes() != null)
                dto.setCommandeIds(vendeur.getCommandes().stream().map(c -> c.getId()).toList());

            utilisateurs.add(dto);
        });

        // 🔹 2. Mapper les livreurs
        livreurRepository.findAll().forEach(livreur -> {
            LivreurDTO dto = new LivreurDTO();
            dto.setId(livreur.getId());
            dto.setNom(livreur.getNom());
            dto.setPrenom(livreur.getPrenom());
            dto.setEmail(livreur.getEmail());
            dto.setLogin(livreur.getLogin());
            dto.setMotDePasse(livreur.getMotDePasse());
            dto.setTelephone(livreur.getTelephone());
            dto.setAdresse(livreur.getAdresse());
            dto.setVille(livreur.getVille());
            dto.setRole(livreur.getRole());
            dto.setResetCode(livreur.getResetCode());
            dto.setResetCodeExpiry(livreur.getResetCodeExpiry());
            dto.setDeviceToken(livreur.getDeviceToken());
            dto.setBloque(livreur.isBloque());
            dto.setMotifRejet(livreur.getMotifRejet());
            dto.setDepotGarantie(livreur.getDepotGarantie());

            // Champs spécifiques livreur
            dto.setDisponible(livreur.isDisponible());
            dto.setCommissionTotale(livreur.getCommissionTotale());
            dto.setEncaissementsTotaux(livreur.getEncaissementsTotaux());
            dto.setDateDernierEncaissement(livreur.getDateDernierEncaissement());
            dto.setSoldeNet(livreur.getSoldeNet());

            if (livreur.getCommandesLivrees() != null)
                dto.setCommandeIds(livreur.getCommandesLivrees().stream().map(c -> c.getId()).toList());

            utilisateurs.add(dto);
        });

        // 🔹 3. Mapper les administrateurs
        adminRepository.findAll().forEach(admin -> {
            AdminDTO dto = new AdminDTO();
            dto.setId(admin.getId());
            dto.setNom(admin.getNom());
            dto.setPrenom(admin.getPrenom());
            dto.setEmail(admin.getEmail());
            dto.setLogin(admin.getLogin());
            dto.setMotDePasse(admin.getMotDePasse());
            dto.setTelephone(admin.getTelephone());
            dto.setAdresse(admin.getAdresse());
            dto.setVille(admin.getVille());
            dto.setRole(admin.getRole());
            dto.setResetCode(admin.getResetCode());
            dto.setResetCodeExpiry(admin.getResetCodeExpiry());
            dto.setDeviceToken(admin.getDeviceToken());
            utilisateurs.add(dto);
        });

        // 🔹 4. Mapper les clients
        clientRepository.findAll().forEach(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setId(client.getId());
            dto.setNom(client.getNom());
            dto.setPrenom(client.getPrenom());
            dto.setEmail(client.getEmail());
            dto.setLogin(client.getLogin());
            dto.setMotDePasse(client.getMotDePasse());
            dto.setTelephone(client.getTelephone());
            dto.setAdresse(client.getAdresse());
            dto.setVille(client.getVille());
            dto.setRole(client.getRole());
            dto.setResetCode(client.getResetCode());
            dto.setResetCodeExpiry(client.getResetCodeExpiry());
            dto.setDeviceToken(client.getDeviceToken());

            if (client.getCommandes() != null)
                dto.setCommandeIds(client.getCommandes().stream().map(c -> c.getId()).toList());

            utilisateurs.add(dto);
        });

        // 🔹 Filtrage par recherche
        List<UtilisateurDTO> filteredBySearch = utilisateurs;
        if (search != null && !search.isEmpty()) {
            String lowerSearch = search.toLowerCase();
            filteredBySearch = utilisateurs.stream()
                .filter(u -> (u.getNom() != null && u.getNom().toLowerCase().contains(lowerSearch))
                    || (u.getPrenom() != null && u.getPrenom().toLowerCase().contains(lowerSearch))
                    || (u.getEmail() != null && u.getEmail().toLowerCase().contains(lowerSearch)))
                .toList();
        }

        // 🔹 Filtrage par rôle
        List<UtilisateurDTO> filteredByRole = filteredBySearch;
        if (role != null && !role.isEmpty()) {
            try {
                RoleUtilisateur roleEnum = RoleUtilisateur.valueOf(role.toUpperCase());
                filteredByRole = filteredBySearch.stream()
                    .filter(u -> u.getRole() == roleEnum)
                    .toList();
            } catch (IllegalArgumentException e) {
                filteredByRole = new ArrayList<>(); // rôle invalide -> vide
            }
        }

        // 🔹 Pagination manuelle
        int start = page * size;
        int end = Math.min(start + size, filteredByRole.size());
        List<UtilisateurDTO> pageContent =
            (start >= end) ? new ArrayList<>() : filteredByRole.subList(start, end);

        Page<UtilisateurDTO> pagedResult =
            new PageImpl<>(pageContent, PageRequest.of(page, size), filteredByRole.size());

        return ResponseEntity.ok(pagedResult);
    }

    // ✅ Vendeurs et livreurs non encore validés par admin
    @GetMapping("/LivreurVendeurNonAccepter")
    public ResponseEntity<Page<UtilisateurDTO>> getLivreurVendeurNonAccepterPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        List<UtilisateurDTO> utilisateurs = new ArrayList<>();

        vendeurRepository.findAllByEstValideParAdminIsNullOrEstValideParAdminFalse().forEach(vendeur -> {
            VendeurDTO dto = new VendeurDTO();
            dto.setId(vendeur.getId());
            dto.setNom(vendeur.getNom());
            dto.setPrenom(vendeur.getPrenom());
            dto.setEmail(vendeur.getEmail());
            dto.setLogin(vendeur.getLogin());
            dto.setTelephone(vendeur.getTelephone());
            dto.setAdresse(vendeur.getAdresse());
            dto.setVille(vendeur.getVille());
            dto.setRole(vendeur.getRole());
            dto.setBloque(vendeur.getBloque());
            dto.setNomEtablissement(vendeur.getNomEtablissement());
            dto.setCategorie(vendeur.getCategorie() != null ? vendeur.getCategorie().name() : null);

            dto.setRegistreCommerce(vendeur.getRegistreCommerce());
            dto.setIdentifiantFiscal(vendeur.getIdentifiantFiscal());
            dto.setRib(vendeur.getRib());
            dto.setMotifRejet(vendeur.getMotifRejet());
            utilisateurs.add(dto);
        });

        livreurRepository.findAllByEstValideParAdminIsNullOrEstValideParAdminFalse().forEach(livreur -> {
            LivreurDTO dto = new LivreurDTO();
            dto.setId(livreur.getId());
            dto.setNom(livreur.getNom());
            dto.setPrenom(livreur.getPrenom());
            dto.setEmail(livreur.getEmail());
            dto.setLogin(livreur.getLogin());
            dto.setTelephone(livreur.getTelephone());
            dto.setAdresse(livreur.getAdresse());
            dto.setVille(livreur.getVille());
            dto.setRole(livreur.getRole());
            dto.setBloque(livreur.isBloque());
            dto.setDepotGarantie(livreur.getDepotGarantie());
            dto.setMotifRejet(livreur.getMotifRejet());
            utilisateurs.add(dto);
        });

        // Pagination manuelle
        int start = page * size;
        int end = Math.min(start + size, utilisateurs.size());
        List<UtilisateurDTO> pageContent =
            (start >= end) ? new ArrayList<>() : utilisateurs.subList(start, end);

        Page<UtilisateurDTO> pagedResult =
            new PageImpl<>(pageContent, PageRequest.of(page, size), utilisateurs.size());

        return ResponseEntity.ok(pagedResult);
    }

    // ✅ Compteurs
    @GetMapping("/count")
    public long getTotalUtilisateurs() {
        return vendeurRepository.count() + livreurRepository.count();
    }

    @GetMapping("/by-role")
    public ResponseEntity<Map<String, Long>> getUtilisateursByRole() {
        return ResponseEntity.ok(Map.of(
            "vendor", vendeurRepository.count(),
            "delivery", livreurRepository.count()
        ));
    }

    // ✅ Bloquer / Débloquer
    @GetMapping("/{role}/{id}/bloquer")
    public ResponseEntity<String> blockUser(@PathVariable String role, @PathVariable Long id) {
        return handleBlockAction(role, id, true, "bloqué");
    }

    @GetMapping("/{role}/{id}/debloquer")
    public ResponseEntity<String> unblockUser(@PathVariable String role, @PathVariable Long id) {
        return handleBlockAction(role, id, false, "débloqué");
    }

    private ResponseEntity<String> handleBlockAction(String role, Long id, boolean bloque, String action) {
        try {
            switch (role.toUpperCase()) {
                case "LIVREUR" -> toggleLivreurBlock(id, bloque);
                case "VENDEUR" -> toggleVendeurBlock(id, bloque);
                default -> throw new RuntimeException("Rôle non supporté : " + role);
            }
            return ResponseEntity.ok("Utilisateur " + action + " avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private void toggleLivreurBlock(Long id, boolean bloque) {
        Livreur livreur = livreurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));
        livreur.setBloque(bloque);
        livreurRepository.save(livreur);
    }

    private void toggleVendeurBlock(Long id, boolean bloque) {
        Vendeur vendeur = vendeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendeur non trouvé"));
        vendeur.setBloque(bloque);
        vendeurRepository.save(vendeur);
    }
}
