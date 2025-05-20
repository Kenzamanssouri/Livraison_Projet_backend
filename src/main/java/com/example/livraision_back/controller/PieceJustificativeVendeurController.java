package com.example.livraision_back.controller;

import com.example.livraision_back.mapper.PieceJustificativeVendeurMapper;
import com.example.livraision_back.model.PieceJustificativeVendeur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.service.PieceJustificativeVendeurSevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/pieces-justificatives-vendeurs")
public class PieceJustificativeVendeurController {

    private final PieceJustificativeVendeurSevice pieceService;

    public PieceJustificativeVendeurController(PieceJustificativeVendeurSevice pieceService) {
        this.pieceService = pieceService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPieces(
        @RequestParam("vendeurId") Long vendeurId,
        @RequestParam("pjFiles") MultipartFile[] pjFiles,
        @RequestParam("typesFichiers") List<String> typesFichiers
    ) {
        if (pjFiles.length != typesFichiers.size()) {
            return ResponseEntity.badRequest().body("Nombre de fichiers différent du nombre de types.");
        }

        try {
            pieceService.enregistrerPieces(vendeurId, pjFiles, typesFichiers);
            return ResponseEntity.ok("Pièces justificatives enregistrées.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }
}
