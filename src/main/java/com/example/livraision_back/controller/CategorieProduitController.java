package com.example.livraision_back.controller;

import com.example.livraision_back.dto.CategorieProduitDTO;
import com.example.livraision_back.dto.ProduitDTO;
import com.example.livraision_back.service.CategorieProduitService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieProduitController {

    private final CategorieProduitService categorieProduitService;

    public CategorieProduitController(CategorieProduitService categorieProduitService) {
        this.categorieProduitService = categorieProduitService;
    }

    // 🔹 CREATE
    @PostMapping
    public ResponseEntity<CategorieProduitDTO> create(
        @RequestBody CategorieProduitDTO dto) {

        CategorieProduitDTO created = categorieProduitService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 🔹 UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CategorieProduitDTO> update(
        @PathVariable Long id,
        @RequestBody CategorieProduitDTO dto) {

        CategorieProduitDTO updated = categorieProduitService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CategorieProduitDTO> getById(
        @PathVariable Long id) {

        return ResponseEntity.ok(categorieProduitService.getById(id));
    }

    // 🔹 GET ALL
    @GetMapping
    public ResponseEntity<List<CategorieProduitDTO>> getAll() {

        return ResponseEntity.ok(categorieProduitService.getAll());
    }
    @GetMapping("/paged")
    public ResponseEntity<Page<CategorieProduitDTO>> getPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(categorieProduitService.getPaged( page, size));
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id) {

        categorieProduitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
