package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.ClientDTO;
import com.example.livraision_back.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client client);

    // Custom implementation to include password hashing
    default Client toEntity(ClientDTO dto, PasswordEncoder passwordEncoder) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setAdresse(dto.getAdresse());
        client.setLogin(dto.getLogin());
        client.setRole(dto.getRole());
        client.setVille(dto.getVille());
        if (dto.getMotDePasse() != null) {
            client.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }

        return client;
    }
}
