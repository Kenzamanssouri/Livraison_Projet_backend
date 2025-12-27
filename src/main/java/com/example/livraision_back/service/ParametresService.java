package com.example.livraision_back.service;

import com.example.livraision_back.model.ParametresPlateforme;

public interface ParametresService {
    public ParametresPlateforme get();
    public ParametresPlateforme save(ParametresPlateforme p);
}
