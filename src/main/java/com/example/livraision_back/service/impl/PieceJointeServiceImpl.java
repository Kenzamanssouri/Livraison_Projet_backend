package com.example.livraision_back.service.impl;


import com.example.livraision_back.model.PieceJointe;
import com.example.livraision_back.repository.PieceJointeRepository;
import com.example.livraision_back.service.PieceJointeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@Service
public class PieceJointeServiceImpl implements PieceJointeService {
    private final PieceJointeRepository pieceJointeRepository;
    private final String uploadDir = "uploads";

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
    }

    public PieceJointe saveFile(MultipartFile file, Long planActionId, String typePjPlanAction) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Build a unique filename, e.g. timestamp + original name to avoid collisions
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = System.currentTimeMillis() + "_" + originalFilename;

        // Save file to disk
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        // Create PieceJointe entity and save metadata in DB
        PieceJointe pj = new PieceJointe();
        pj.setName(originalFilename);
        pj.setUrl("/" + uploadDir + "/" + uniqueFileName);  // This URL can be adjusted depending on your static resources setup
        pj.setSize(file.getSize());
        pj.setType(file.getContentType());
        pj.setIdObjet(planActionId);
        pj.setTypePj(typePjPlanAction);
        return pieceJointeRepository.save(pj);
    }
    public List<PieceJointe> getByPlanActionId(Long planActionId, String typePjPlanAction) {
        return pieceJointeRepository.findByIdObjetAndTypePj(planActionId,typePjPlanAction);
    }
    public List<PieceJointe> getByType(String typePjPlanAction) {
        return pieceJointeRepository.findByTypePj(typePjPlanAction);
    }
    public void deletePieceJointe(Long id) {
        Optional<PieceJointe> pjOptional = pieceJointeRepository.findById(id);
        if (pjOptional.isPresent()) {
            PieceJointe pj = pjOptional.get();

            // Example: url = "/uploads/1747751030325_Plan triennal (2).pdf"
            String filePath = pj.getUrl();
            if (filePath.startsWith("/uploads/")) {
                filePath = filePath.substring("/uploads/".length()); // Keep only the filename
            }

            Path uploadPath = Paths.get("uploads").resolve(filePath); // Relative to working dir

            try {
                boolean deleted = Files.deleteIfExists(uploadPath);
                if (!deleted) {
                    System.out.println("File not found: " + uploadPath.toAbsolutePath());
                } else {
                    System.out.println("File deleted: " + uploadPath.toAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file: " + uploadPath.toAbsolutePath(), e);
            }

            pieceJointeRepository.deleteById(id);
        } else {
            throw new RuntimeException("PieceJointe not found with id: " + id);
        }
    }
}
