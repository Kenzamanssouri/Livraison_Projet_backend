package com.example.livraision_back.service.impl;

import com.example.livraision_back.model.Admin;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.repository.AdminRepository;
import com.example.livraision_back.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository, AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = clientRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getMotDePasse(),
            new ArrayList<>()
        );
    }
    public UserDetails loadUserByEmailAndRole(String email, int role) {
        switch (role) {
            case 0:
                return loadUserByUsername(email);
            case 3:
                return loadUserByUsernameAdmin(email);
            // Ajoutez d'autres cas pour livreur, vendeur, etc.
            default:
                throw new UsernameNotFoundException("Role inconnu : " + role);
        }
    }

    public UserDetails loadUserByUsernameAdmin(String email) throws UsernameNotFoundException {
        Admin user = adminRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getMotDePasse(),
            new ArrayList<>()
        );
    }

}
