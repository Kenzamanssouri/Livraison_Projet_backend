package com.example.livraision_back.service;

import com.example.livraision_back.dto.PieceJustificativeVendeurDTO;
import com.example.livraision_back.model.PieceJustificativeVendeur;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PieceJustificativeVendeurSevice {
    PieceJustificativeVendeur save(PieceJustificativeVendeurDTO dto);
    void enregistrerPieces(Long vendeurId, MultipartFile[] files, List<String> typesFichiers) throws IOException;
}
