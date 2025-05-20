package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.VendeurDTO;
import com.example.livraision_back.model.Horaire;
import com.example.livraision_back.model.Vendeur;
import com.example.livraision_back.repository.VendeurRepository;
import com.example.livraision_back.service.VendeurService;
import com.example.livraision_back.specification.VendeurSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendeurServiceImpl implements VendeurService {

    private final VendeurRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public VendeurServiceImpl(VendeurRepository clientRepository,
                              PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Vendeur save(Vendeur clientDTO) {
        clientDTO.setMotDePasse(clientDTO.getMotDePasse() != null ? passwordEncoder.encode(clientDTO.getMotDePasse()) : null);


        return clientRepository.save(clientDTO);
    }

    @Override
    public List<Vendeur> findAll() {
        return clientRepository.findAll();
    }
    @Override
    public Page<Vendeur> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Vendeur findById(Long id) {
        Optional<Vendeur> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    @Override
    public Vendeur update(Long id, VendeurDTO clientDTO) {
        Optional<Vendeur> optionalVendeur = clientRepository.findById(id);
        if (optionalVendeur.isPresent()) {
            Vendeur existingVendeur = optionalVendeur.get();

            // Vérifie si le login a changé
            if (!existingVendeur.getLogin().equals(clientDTO.getLogin())) {
                Optional<Vendeur> clientWithSameLogin = clientRepository.findByLogin(clientDTO.getLogin());
                if (clientWithSameLogin.isPresent()) {
                    throw new RuntimeException("Ce login est déjà utilisé par un autre utilisateur.");
                }
                existingVendeur.setLogin(clientDTO.getLogin());
            }

            // Mise à jour des champs communs
            existingVendeur.setNom(clientDTO.getNom());
            existingVendeur.setPrenom(clientDTO.getPrenom());
            existingVendeur.setEmail(clientDTO.getEmail());
            existingVendeur.setTelephone(clientDTO.getTelephone());
            existingVendeur.setAdresse(clientDTO.getAdresse());
            existingVendeur.setVille(clientDTO.getVille());
            existingVendeur.setRole(clientDTO.getRole());

            if (clientDTO.getMotDePasse() != null && !clientDTO.getMotDePasse().isEmpty()) {
                existingVendeur.setMotDePasse(passwordEncoder.encode(clientDTO.getMotDePasse()));
            }

            // Champs spécifiques à Vendeur
            existingVendeur.setNomEtablissement(clientDTO.getNomEtablissement());
            existingVendeur.setCategorie(clientDTO.getCategorie());
            existingVendeur.setRegistreCommerce(clientDTO.getRegistreCommerce());
            existingVendeur.setIdentifiantFiscal(clientDTO.getIdentifiantFiscal());
            existingVendeur.setRib(clientDTO.getRib());
            existingVendeur.setEstValideParAdmin(clientDTO.isEstValideParAdmin());

            // Mise à jour de l'horaire d'ouverture s'il existe
            if (clientDTO.getHoraireOuverture() != null) {
                Horaire horaire = new Horaire();
                horaire.setJour(clientDTO.getHoraireOuverture().getJour());
                horaire.setHeureOuverture(clientDTO.getHoraireOuverture().getHeureOuverture());
                horaire.setHeureFermeture(clientDTO.getHoraireOuverture().getHeureFermeture());
                existingVendeur.setHoraireOuverture(horaire);
            } else {
                existingVendeur.setHoraireOuverture(null);
            }

            return clientRepository.save(existingVendeur);
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
    public Page<Vendeur> searchVendeurs(VendeurDTO filter, Pageable pageable) {
        Specification<Vendeur> spec = Specification.where(VendeurSpecification.hasNom(filter.getNom()))
            .and(VendeurSpecification.hasPrenom(filter.getPrenom()))
            .and(VendeurSpecification.hasEmail(filter.getEmail()))
            .and(VendeurSpecification.hasLogin(filter.getLogin()))
            .and(VendeurSpecification.hasTelephone(filter.getTelephone()))
            .and(VendeurSpecification.hasVille(filter.getVille()))
            .and(VendeurSpecification.hasNomEtablissement(filter.getNomEtablissement()))
            .and(VendeurSpecification.hasCategorie(filter.getCategorie()))
            .and(VendeurSpecification.hasRegistreCommerce(filter.getRegistreCommerce()))
            .and(VendeurSpecification.hasIdentifiantFiscal(filter.getIdentifiantFiscal()))
            .and(VendeurSpecification.hasRib(filter.getRib()))
            .and(VendeurSpecification.isValideParAdmin(filter.isEstValideParAdmin()));

        return clientRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

}
