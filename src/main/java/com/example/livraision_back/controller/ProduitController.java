package com.example.livraision_back.controller;

import com.example.livraision_back.dto.ProduitDTO;
import com.example.livraision_back.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    // ➕ AJOUT PRODUIT (CATEGORIE & OPTIONS NULL POSSIBLES)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ProduitDTO> addProduit(    @RequestBody ProduitDTO dto
    ) {
        ProduitDTO saved = produitService.addProduit(dto);
        return ResponseEntity.ok(saved);
    }


    // 📄 PAGINATION + FILTRE (nom)
    @GetMapping("/paged")
    public ResponseEntity<Page<ProduitDTO>> getPaged(
        @RequestParam Long vendeurId,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(produitService.getPaged(vendeurId, search, page, size));
    }

    // 📝 DETAIL PRODUIT
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    // ✏ MODIFIER PRODUIT
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(
        @PathVariable Long id, @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.update(id, dto));
    }

    // ❌ SUPPRESSION
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }

    // 🔄 ACTIVER / DESACTIVER PRODUIT
    @PutMapping("/{id}/toggle")
    public ResponseEntity<?> toggle(@PathVariable Long id) {
        boolean status = produitService.toggle(id);
        return ResponseEntity.ok(Map.of("active", status));
    }
}
