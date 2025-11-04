package com.example.livraision_back.controller;

import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Notification;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.service.VendeurService;
import com.example.livraision_back.service.impl.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendeurs")
public class VendeurController {
    private final VendeurService clientService;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public VendeurController(VendeurService clientService, NotificationRepository notificationRepository, EmailService emailService) {
        this.clientService = clientService;
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<?> createVendeur(@RequestBody Vendeur clientDTO) {
        clientDTO.setEstValideParAdmin(null);
        // Check if a client already exists with this email
        boolean exists = clientService.existsByEmail(clientDTO.getEmail());

        if (exists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("An account with this email already exists.");
        }

        Vendeur savedVendeur = clientService.save(clientDTO);
        Notification notification=new Notification();
        notification.setMessage("le vendeur "+savedVendeur.getNom()+" "+savedVendeur.getPrenom()+" est crée");
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
            clientService.save(client);
            emailService.sendValidationEmail(client.getEmail(), client.getNom());
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
            emailService.sendRefusEmail(client.getEmail(), client.getNom(),motif);
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
