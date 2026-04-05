package com.example.livraision_back.controller;

import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.dto.CreateCommandeRequest;
import com.example.livraision_back.dto.DashboardVendeurResponse;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.model.Commande;
import com.example.livraision_back.model.StatutCommande;
import com.example.livraision_back.service.CommandeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }


    @GetMapping("/paged")
    public ResponseEntity<Page<CommandeDTO>> getAllCommandesWithPages(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Page<CommandeDTO> clients = commandeService.findAll(page,size);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<CommandeDTO>> getAllCommandes() {
        List<CommandeDTO> clients = commandeService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    // 🔹 Nouveau endpoint
    @GetMapping("/by-status")
    public ResponseEntity<?> getCommandesByStatus() {
        List<CommandeDTO> commandes = commandeService.findAll();

        // Map statut -> count
        Map<String, Long> countByStatus = Arrays.stream(StatutCommande.values())
            .collect(Collectors.toMap(
                StatutCommande::name,
                status -> commandes.stream().filter(c -> c.getStatut() == status).count()
            ));

        // Optionnel : total commandes
        long totalOrders = commandes.size();

        return ResponseEntity.ok(Map.of(
            "ordersByStatus", countByStatus,
            "totalOrders", totalOrders
        ));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardVendeurResponse> getDashboard(@RequestParam Long vendeurId) {
        DashboardVendeurResponse dashboard = commandeService.getDashboard(vendeurId);
        return ResponseEntity.ok(dashboard);
    }
    @PostMapping("/commandes")
    public Commande creerCommandeTexteLibre(
        @RequestBody CreateCommandeRequest req) {


        return commandeService.creerCommandeTexteLibre(
            req.getUsername(),
            req.getDescription(),
            req.getAdresse()
        );
    }

    @GetMapping("/livreur/commandes")
    public ResponseEntity<List<CommandeDTO>> getCommandesLivreur(
        @RequestParam(required = false) Long livreurId,
        @RequestParam(required = false) StatutCommande statut
    ) {
        return ResponseEntity.ok(
            commandeService.findByLivreurOrStatut(livreurId, statut)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable Long id) {
        CommandeDTO commande = commandeService.findById(id);
        return ResponseEntity.ok(commande);
    }
}
