package com.example.livraision_back.controller;

import com.example.livraision_back.dto.HoraireDTO;
import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.CategorieVendeur;
import com.example.livraision_back.model.Horaire;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.service.VendeurService;
import com.example.livraision_back.service.impl.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vendeurs")
public class VendeurController {
    private final VendeurService clientService;
    private final VendeurRepository clientRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public VendeurController(VendeurService clientService, VendeurRepository clientRepository, NotificationRepository notificationRepository, EmailService emailService) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<?> createVendeur(@RequestBody VendeurDTO clientDTO) {

        // Vérifie si un client existe déjà avec le même email
        boolean exists = clientService.existsByEmail(clientDTO.getEmail());
        if (exists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("An account with this email already exists.");
        }
        // Vérifie si un compte existe déjà avec le même login
        boolean loginExists = clientService.existsByLogin(clientDTO.getLogin());
        if (loginExists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Un compte avec ce login existe déjà.");
        }
        // Création de l'entité Vendeur
        Vendeur vendeur = new Vendeur();
        vendeur.setNom(clientDTO.getNom());
        vendeur.setPrenom(clientDTO.getPrenom());
        vendeur.setEmail(clientDTO.getEmail());
        vendeur.setLogin(clientDTO.getLogin());
        vendeur.setMotDePasse(clientDTO.getMotDePasse()); // Encode si nécessaire
        vendeur.setTelephone(clientDTO.getTelephone());
        vendeur.setAdresse(clientDTO.getAdresse());
        vendeur.setVille(clientDTO.getVille());
        vendeur.setRole(clientDTO.getRole());

        // Champs spécifiques Vendeur
        vendeur.setNomEtablissement(clientDTO.getNomEtablissement());
        if (clientDTO.getCategorie() != null) {
            try {
                vendeur.setCategorie(CategorieVendeur.valueOf(
                    clientDTO.getCategorie()
                        .toUpperCase()
                        .replace("É", "E")
                        .replace("È", "E")
                        .replace("À", "A")
                        .replace(" ", "_")
                ));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Catégorie vendeur invalide : " + clientDTO.getCategorie());
            }
        }
        vendeur.setRegistreCommerce(clientDTO.getRegistreCommerce());
        vendeur.setIdentifiantFiscal(clientDTO.getIdentifiantFiscal());
        vendeur.setRib(clientDTO.getRib());
        vendeur.setEstValideParAdmin(clientDTO.getEstValideParAdmin());

        // Gestion des horaires multiples
        if (clientDTO.getHorairesOuverture() != null) {
            List<Horaire> horaires = new ArrayList<>();
            for (HoraireDTO dto : clientDTO.getHorairesOuverture()) {
                Horaire h = new Horaire();
                h.setJour(dto.getJour());
                h.setHeureOuverture(dto.getHeureOuverture());
                h.setHeureFermeture(dto.getHeureFermeture());
                h.setVendeur(vendeur); // lien bidirectionnel
                horaires.add(h);
            }
            vendeur.setHorairesOuverture(horaires);
        }

        // Sauvegarde du vendeur
        Vendeur savedVendeur = clientService.save(vendeur);
        emailService.sendCreationVendeur(savedVendeur.getEmail(), savedVendeur.getNom());

        // Notification
        Notification notification = new Notification();
        notification.setMessage("Le vendeur " + savedVendeur.getNom() + " " + savedVendeur.getPrenom() + " est créé");
        notification.setIdObject(savedVendeur.getId());
        notification.setObject("Vendeur");
        notificationRepository.save(notification);

        return new ResponseEntity<>(savedVendeur, HttpStatus.CREATED);
    }


    /**
     * Format a Moroccan phone number to international format.
     * Example: 0612345678 => +212612345678
     */
    private String formatMoroccanPhoneNumber(String localPhoneNumber) {
        if (localPhoneNumber.startsWith("0")) {
            return "+212" + localPhoneNumber.substring(1);
        }
        // If already international format, return as-is
        return localPhoneNumber;
    }



    // READ - Get all clients
    @GetMapping
    public ResponseEntity<List<Vendeur>> getAllVendeurs() {
        List<Vendeur> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/paged")
    public ResponseEntity<Page<Vendeur>> getVendeursPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir // ← Ajouté
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Vendeur> clientsPage = clientService.findAll(pageable);
        return new ResponseEntity<>(clientsPage, HttpStatus.OK);
    }


    // READ - Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vendeur> getVendeurById(@PathVariable Long id) {
        Vendeur client = clientService.findById(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Valide
    @GetMapping("/{id}/accept")
    public ResponseEntity<Vendeur> ValideVendeurById(@PathVariable Long id) {
        Vendeur client = clientService.findById(id);

        if (client != null) {
            client.setEstValideParAdmin(true);
            clientRepository.save(client);
            emailService.sendValidationEmailVendeur(client.getEmail(), client.getNom());
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // refuse
    @GetMapping("/{id}/refuse/{motif}")
    public ResponseEntity<Vendeur> refiuseVendeurById(@PathVariable Long id,@PathVariable String motif) {
        Vendeur client = clientService.findById(id);
        if (client != null) {
            client.setEstValideParAdmin(false);
            client.setMotifRejet(motif);
            clientService.save(client);
            emailService.sendRefusEmailVendeur(client.getEmail(), client.getNom(),motif);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Vendeur> updateVendeur(@PathVariable Long id, @RequestBody VendeurDTO clientDTO) {
        Vendeur updatedVendeur = clientService.update(id, clientDTO);
        if (updatedVendeur != null) {
            return new ResponseEntity<>(updatedVendeur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendeur(@PathVariable Long id) {
        boolean deleted = clientService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<Page<Vendeur>> searchVendeurs(
        @RequestBody VendeurDTO filter,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Vendeur> result = clientService.searchVendeurs(filter, pageable);
        return ResponseEntity.ok(result);
    }

}
