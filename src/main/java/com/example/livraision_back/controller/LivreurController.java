package com.example.livraision_back.controller;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.service.LivreurService;
import com.example.livraision_back.service.impl.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {
    private final LivreurService clientService;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public LivreurController(LivreurService clientService, NotificationRepository notificationRepository, EmailService emailService) {
        this.clientService = clientService;
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }


    @GetMapping("/{id}/accept")
    public ResponseEntity<LivreurDTO> ValideVendeurById(@PathVariable Long id) {
        LivreurDTO client = clientService.findById(id);

        if (client != null) {
            client.setEstValideParAdmin(true);
            clientService.save(client);
            emailService.sendValidationEmail(client.getEmail(), client.getNom());
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/refuse/{motif}")
    public ResponseEntity<LivreurDTO> refiuseVendeurById(@PathVariable Long id, @PathVariable String motif) {
        LivreurDTO client = clientService.findById(id);

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

    @GetMapping
    public ResponseEntity<Page<LivreurDTO>> findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseEntity<>(clientService.findAll(pageRequest),HttpStatus.OK);
    }
}
