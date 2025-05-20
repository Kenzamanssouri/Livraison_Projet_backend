package com.example.livraision_back.service;

import com.example.livraision_back.dto.ClientDTO;
import com.example.livraision_back.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    Client save(ClientDTO dto);
    List<Client> findAll();
    Page<Client> findAll(Pageable pageable);

    Client findById(Long id);
    Client update(Long id, ClientDTO dto);
    boolean delete(Long id);

    Page<Client> searchClients(ClientDTO filter, Pageable pageable);

     boolean existsByEmail(String email);
}
