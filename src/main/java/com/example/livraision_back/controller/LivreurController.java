package com.example.livraision_back.controller;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.mapper.LivreurMapper;
import com.example.livraision_back.model.GeoLocalisation;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.Notification;
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
    @PostMapping
    public ResponseEntity<?> createLivreur(@RequestBody LivreurDTO livreurDTO) {

        // Vérifie si un compte existe déjà avec le même email
        boolean emailExists = clientService.existsByEmail(livreurDTO.getEmail());
        if (emailExists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Un compte avec cet email existe déjà.");
        }

        // Vérifie si un compte existe déjà avec le même login
        boolean loginExists = clientService.existsByLogin(livreurDTO.getLogin());
        if (loginExists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Un compte avec ce login existe déjà.");
        }

        // Création de l'entité Livreur
        Livreur livreur = new Livreur();
        livreur.setNom(livreurDTO.getNom());
        livreur.setPrenom(livreurDTO.getPrenom());
        livreur.setLogin(livreurDTO.getLogin());
        livreur.setEmail(livreurDTO.getEmail());
        livreur.setTelephone(livreurDTO.getTelephone());
        livreur.setAdresse(livreurDTO.getAdresse());
        livreur.setVille(livreurDTO.getVille());
        livreur.setRole(livreurDTO.getRole());
        livreur.setMotDePasse(livreurDTO.getMotDePasse()); // Encode si nécessaire

        // Champs spécifiques au livreur
        livreur.setDisponible(livreurDTO.isDisponible());
        if (livreurDTO.getPositionActuelle() != null) {
            GeoLocalisation position = new GeoLocalisation();
            position.setLatitude(livreurDTO.getPositionActuelle().getLatitude());
            position.setLongitude(livreurDTO.getPositionActuelle().getLongitude());
            livreur.setPositionActuelle(position);
        }

        livreur.setBloque(livreurDTO.isBloque());
        livreur.setEstValideParAdmin(livreurDTO.getEstValideParAdmin());
        livreur.setMotifRejet(livreurDTO.getMotifRejet());
        livreur.setDepotGarantie(livreurDTO.getDepotGarantie());

        // Commission et encaissements initialisés à 0 si null
        livreur.setCommissionTotale(livreurDTO.getCommissionTotale());
        livreur.setEncaissementsTotaux(livreurDTO.getEncaissementsTotaux());
        livreur.setDateDernierEncaissement(livreurDTO.getDateDernierEncaissement());

        LivreurDTO livreurDTO1 = LivreurMapper.toDTO(livreur);

        // Sauvegarde du livreur
        LivreurDTO savedLivreur = clientService.save(livreurDTO1);

        // Notification
        Notification notification = new Notification();
        notification.setMessage("Le livreur " + savedLivreur.getNom() + " " + savedLivreur.getPrenom() + " est créé");
        notification.setIdObject(savedLivreur.getId());
        notification.setObject("Livreur");
        notificationRepository.save(notification);

        return new ResponseEntity<>(savedLivreur, HttpStatus.CREATED);
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
