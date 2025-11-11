package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.LivreurDTO;
import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.mapper.LivreurMapper;
import com.example.livraision_back.model.Horaire;
import com.example.livraision_back.model.Livreur;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.LivreurRepository;
import com.example.livraision_back.service.LivreurService;
import com.example.livraision_back.specification.VendeurSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final LivreurMapper livreurMapper;
    public LivreurServiceImpl(LivreurRepository clientRepository,
                              PasswordEncoder passwordEncoder, LivreurMapper livreurMapper) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.livreurMapper = livreurMapper;
    }

    @Override
    public LivreurDTO save(LivreurDTO clientDTO) {
        // Encoder le mot de passe si fourni
        clientDTO.setMotDePasse(
            clientDTO.getMotDePasse() != null
                ? passwordEncoder.encode(clientDTO.getMotDePasse())
                : null
        );

        // Convertir DTO en entité
        Livreur clientEntity = LivreurMapper.toEntity(clientDTO);

        // Sauvegarder l'entité
        Livreur savedClient = clientRepository.save(clientEntity);

        // Convertir l'entité sauvegardée en DTO et retourner
        return LivreurMapper.toDTO(savedClient);
    }


    @Override
    public List<LivreurDTO> findAll() {
        List<Livreur> livreurs = clientRepository.findAll();
        return livreurs.stream()
            .map(LivreurMapper::toDTO) // Mapper chaque entité en DTO
            .toList();
    }

    @Override
    public Page<LivreurDTO> findAll(Pageable pageable) {
        Page<Livreur> livreurs = clientRepository.findAll(pageable);
        return livreurs.map(LivreurMapper::toDTO); // map() sur Page fonctionne directement
    }


    @Override
    public LivreurDTO findById(Long id) {
        return clientRepository.findById(id)
            .map(LivreurMapper::toDTO)  // transforme Livreur en LivreurDTO
            .orElse(null);              // retourne null si non trouvé
    }


    @Override
    public LivreurDTO update(Long id, LivreurDTO clientDTO) {
        Optional<Livreur> optionalLivreur = clientRepository.findById(id);

        if (optionalLivreur.isPresent()) {
            Livreur existingLivreur = optionalLivreur.get();

            // Vérifie si le login a changé
            if (!existingLivreur.getLogin().equals(clientDTO.getLogin())) {
                Optional<Livreur> clientWithSameLogin = clientRepository.findByLogin(clientDTO.getLogin());
                if (clientWithSameLogin.isPresent()) {
                    throw new RuntimeException("Ce login est déjà utilisé par un autre utilisateur.");
                }
                existingLivreur.setLogin(clientDTO.getLogin());
            }

            // Mise à jour des champs communs
            existingLivreur.setNom(clientDTO.getNom());
            existingLivreur.setPrenom(clientDTO.getPrenom());
            existingLivreur.setEmail(clientDTO.getEmail());
            existingLivreur.setTelephone(clientDTO.getTelephone());
            existingLivreur.setAdresse(clientDTO.getAdresse());
            existingLivreur.setVille(clientDTO.getVille());
            existingLivreur.setRole(clientDTO.getRole());

            if (clientDTO.getMotDePasse() != null && !clientDTO.getMotDePasse().isEmpty()) {
                existingLivreur.setMotDePasse(passwordEncoder.encode(clientDTO.getMotDePasse()));
            }

            existingLivreur.setEstValideParAdmin(clientDTO.getEstValideParAdmin());

            // Sauvegarde et conversion en DTO
            Livreur savedLivreur = clientRepository.save(existingLivreur);
            return LivreurMapper.toDTO(savedLivreur);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
    @Override
    public boolean existsByLogin(String login) {
        return clientRepository.existsByLogin(login);
    }

}
