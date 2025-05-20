package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.AdminDTO;
import com.example.livraision_back.mapper.AdminMapper;
import com.example.livraision_back.model.Admin;
import com.example.livraision_back.repository.AdminRepository;
import com.example.livraision_back.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin save(AdminDTO clientDTO) {
        Admin client = adminMapper.toEntity(clientDTO, passwordEncoder);
        return adminRepository.save(client);
    }
}
