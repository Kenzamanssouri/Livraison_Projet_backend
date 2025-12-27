package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.CategorieProduitDTO;
import com.example.livraision_back.mapper.CategorieProduitMapper;
import com.example.livraision_back.model.CategorieProduit;
import com.example.livraision_back.repository.CategorieProduitRepository;
import com.example.livraision_back.service.CategorieProduitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategorieProduitServiceImpl implements CategorieProduitService {

    private final CategorieProduitRepository repository;
    private final CategorieProduitMapper mapper;

    public CategorieProduitServiceImpl(CategorieProduitRepository repository,
                                       CategorieProduitMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategorieProduitDTO create(CategorieProduitDTO dto) {

        if (repository.existsByNom(dto.getNom())) {
            throw new RuntimeException("La catégorie existe déjà");
        }

        CategorieProduit entity = mapper.toEntity(dto);
        CategorieProduit saved = repository.save(entity);

        return mapper.toDto(saved);
    }

    @Override
    public CategorieProduitDTO update(Long id, CategorieProduitDTO dto) {

        CategorieProduit existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        // éviter doublon sur le nom
        if (!existing.getNom().equals(dto.getNom())
            && repository.existsByNom(dto.getNom())) {
            throw new RuntimeException("Une catégorie avec ce nom existe déjà");
        }

        existing.setNom(dto.getNom());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieProduitDTO getById(Long id) {

        return repository.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieProduitDTO> getAll() {

        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Catégorie introuvable");
        }

        repository.deleteById(id);
    }
}
