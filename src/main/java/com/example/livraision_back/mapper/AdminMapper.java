package com.example.livraision_back.mapper;

import com.example.livraision_back.dto.AdminDTO;
import com.example.livraision_back.model.Admin;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")

public interface AdminMapper {
    AdminDTO toDTO(Admin client);

    // Custom implementation to include password hashing
    default Admin toEntity(AdminDTO dto, PasswordEncoder passwordEncoder) {
        if (dto == null) {
            return null;
        }

        Admin client = new Admin();
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
