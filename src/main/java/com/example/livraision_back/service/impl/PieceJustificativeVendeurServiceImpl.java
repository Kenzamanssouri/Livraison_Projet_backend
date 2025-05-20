package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.PieceJustificativeVendeurDTO;
import com.example.livraision_back.mapper.PieceJustificativeVendeurMapper;
import com.example.livraision_back.model.PieceJustificativeVendeur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.PieceJustificativeVendeurRepository;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.service.PieceJustificativeVendeurSevice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PieceJustificativeVendeurServiceImpl implements PieceJustificativeVendeurSevice {
    private final PieceJustificativeVendeurRepository pieceJustificativeVendeurRepository;
    private final VendeurRepository vendeurRepository;
    private final PieceJustificativeVendeurMapper pieceJustificativeVendeurMapper;

    public PieceJustificativeVendeurServiceImpl(PieceJustificativeVendeurRepository pieceJustificativeVendeurRepository, VendeurRepository vendeurRepository, PieceJustificativeVendeurMapper pieceJustificativeVendeurMapper) {
        this.pieceJustificativeVendeurRepository = pieceJustificativeVendeurRepository;
        this.vendeurRepository = vendeurRepository;
        this.pieceJustificativeVendeurMapper = pieceJustificativeVendeurMapper;
    }
    public PieceJustificativeVendeur save(PieceJustificativeVendeurDTO dto){
        return pieceJustificativeVendeurRepository.save(pieceJustificativeVendeurMapper.toEntity(dto));
    }


    public void enregistrerPieces(Long vendeurId, MultipartFile[] files, List<String> typesFichiers) throws IOException {
        Optional<Vendeur> vendeurOpt = vendeurRepository.findById(vendeurId);
        if (!vendeurOpt.isPresent()) {
            throw new RuntimeException("Vendeur non trouvé");
        }

        Vendeur vendeur = vendeurOpt.get();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String type = typesFichiers.get(i);

            PieceJustificativeVendeur pj = new PieceJustificativeVendeur();
            pj.setNomFichier(file.getOriginalFilename());
            pj.setTypeFichier(type);
            pj.setContenu(file.getBytes());
            pj.setVendeur(vendeur);

            pieceJustificativeVendeurRepository.save(pj);
        }
    }
}
