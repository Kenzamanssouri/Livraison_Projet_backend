package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.ProduitDTO;
import com.example.livraision_back.mapper.ProduitMapper;
import com.example.livraision_back.model.CategorieProduit;
import com.example.livraision_back.model.Produit;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.CategorieProduitRepository;
import com.example.livraision_back.repository.ProduitRepository;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.service.FileStorageService;
import com.example.livraision_back.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final VendeurRepository vendeurRepository;
    private final CategorieProduitRepository categorieRepository;
    private final FileStorageService fileStorageService;
    private final ProduitMapper mapper;

    public ProduitServiceImpl(
        ProduitRepository produitRepository,
        VendeurRepository vendeurRepository,
        CategorieProduitRepository categorieRepository,
        FileStorageService fileStorageService,
        ProduitMapper mapper
    ) {
        this.produitRepository = produitRepository;
        this.vendeurRepository = vendeurRepository;
        this.categorieRepository = categorieRepository;
        this.fileStorageService = fileStorageService;
        this.mapper = mapper;
    }

    /* ================= CREATE ================= */
    @Override
    public ProduitDTO addProduit(ProduitDTO dto) {

        Produit produit = mapper.toEntity(dto);

        // 🔹 vendeur
        Vendeur vendeur = vendeurRepository.findById(dto.getVendeur().getId())
            .orElseThrow(() -> new RuntimeException("Vendeur introuvable"));
        produit.setVendeur(vendeur);

        // 🔹 catégorie
        CategorieProduit categorie = categorieRepository.findById(dto.getCategorie().getId())
            .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        produit.setCategorie(categorie);

        // 🔹 image


        produit.setActif(true);

        Produit saved = produitRepository.save(produit);
        return mapper.toDto(saved);
    }

    /* ================= PAGINATION ================= */
    @Override
    public Page<ProduitDTO> getPaged(Long vendeurId, String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Produit> results;

        if (search != null && !search.isBlank()) {
            results = produitRepository
                .findByVendeurIdAndNomContainingIgnoreCase(vendeurId, search, pageable);
        } else {
            results = produitRepository.findByVendeurId(vendeurId, pageable);
        }

        return results.map(mapper::toDto);
    }

    /* ================= GET BY ID ================= */
    @Override
    public ProduitDTO getById(Long id) {

        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        return mapper.toDto(produit);
    }

    /* ================= UPDATE ================= */
    @Override
    public ProduitDTO update(Long id, ProduitDTO dto) {

        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setPrix(dto.getPrix());

        if (dto.getCategorie() != null) {
            CategorieProduit categorie = categorieRepository.findById(dto.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
            produit.setCategorie(categorie);
        }

        Produit updated = produitRepository.save(produit);
        return mapper.toDto(updated);
    }

    /* ================= DELETE ================= */
    @Override
    public void delete(Long id) {
        produitRepository.deleteById(id);
    }

    /* ================= TOGGLE ================= */
    @Override
    public boolean toggle(Long id) {

        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        produit.setActif(!produit.getActif());
        produitRepository.save(produit);

        return produit.getActif();
    }
}
