package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.ClientDTO;
import com.example.livraision_back.mapper.ClientMapper;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.repository.ClientRepository;
import com.example.livraision_back.service.ClientService;
import com.example.livraision_back.specification.ClientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository,
                             ClientMapper clientMapper,
                             PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client save(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO, passwordEncoder);
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    @Override
    public Client update(Long id, ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();

            // Vérifie si le login a changé
            if (!existingClient.getLogin().equals(clientDTO.getLogin())) {
                Optional<Client> clientWithSameLogin = clientRepository.findByLogin(clientDTO.getLogin());
                if (clientWithSameLogin.isPresent()) {
                    throw new RuntimeException("Ce login est déjà utilisé par un autre utilisateur.");
                }
                existingClient.setLogin(clientDTO.getLogin());
            }

            // Mise à jour des autres champs
            existingClient.setNom(clientDTO.getNom());
            existingClient.setPrenom(clientDTO.getPrenom());
            existingClient.setEmail(clientDTO.getEmail());
            existingClient.setTelephone(clientDTO.getTelephone());
            existingClient.setAdresse(clientDTO.getAdresse());
            existingClient.setRole(clientDTO.getRole());

            if (clientDTO.getMotDePasse() != null && !clientDTO.getMotDePasse().isEmpty()) {
                existingClient.setMotDePasse(passwordEncoder.encode(clientDTO.getMotDePasse()));
            }

            return clientRepository.save(existingClient);
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
    public Page<Client> searchClients(ClientDTO filter, Pageable pageable) {
        Specification<Client> spec = Specification.where(ClientSpecification.hasNom(filter.getNom()))
            .and(ClientSpecification.hasPrenom(filter.getPrenom()))
            .and(ClientSpecification.hasEmail(filter.getEmail()))
            .and(ClientSpecification.hasLogin(filter.getLogin()))
            .and(ClientSpecification.hasTelephone(filter.getTelephone()));

        return clientRepository.findAll(spec, pageable);
    }
    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

}
