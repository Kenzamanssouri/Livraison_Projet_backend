package com.example.livraision_back.service.impl;

import com.example.livraision_back.model.ParametresPlateforme;
import com.example.livraision_back.repository.ParametresRepository;
import com.example.livraision_back.service.ParametresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametresServiceImpl implements ParametresService {
    @Autowired
    private ParametresRepository repo;

    public ParametresPlateforme get() {
        return repo.findById(1L)
            .orElse(new ParametresPlateforme());
    }

    public ParametresPlateforme save(ParametresPlateforme p) {
        p.setId(1L);
        return repo.save(p);
    }
}
