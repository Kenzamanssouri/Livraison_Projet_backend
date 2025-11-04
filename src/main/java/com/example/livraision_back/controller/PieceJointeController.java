package com.example.livraision_back.controller;

import com.example.livraision_back.model.PieceJointe;
import com.example.livraision_back.service.PieceJointeService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/uploadPj1")
public class PieceJointeController {
    private final PieceJointeService pieceJointeService;

    public PieceJointeController(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PieceJointe> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("Id") Long Id, String typePjPlanAction) {
        try {
            PieceJointe savedPj = pieceJointeService.saveFile(file,Id,typePjPlanAction);
            return ResponseEntity.ok(savedPj);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/by-Id-type/{planActionId}/{typePjPlanAction}")
    public ResponseEntity<List<PieceJointe>> getByPlanActionId(@PathVariable Long planActionId,@PathVariable String typePjPlanAction) {
        List<PieceJointe> piecesJointes = pieceJointeService.getByPlanActionId(planActionId,typePjPlanAction);
        if (piecesJointes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(piecesJointes);
    }

    @GetMapping("/by-type/{typePjPlanAction}")
    public ResponseEntity<List<PieceJointe>> getByType(@PathVariable String typePjPlanAction) {
        List<PieceJointe> piecesJointes = pieceJointeService.getByType(typePjPlanAction);
        if (piecesJointes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(piecesJointes);
    }
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // Path where files are stored on your server
            Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = "application/octet-stream";

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        } catch (MalformedURLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/pieces-jointes/{id}")
    public ResponseEntity<Void> deletePieceJointe(@PathVariable Long id) {
        pieceJointeService.deletePieceJointe(id);
        return ResponseEntity.noContent().build();
    }

}
