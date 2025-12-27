package com.example.livraision_back.controller;

import com.example.livraision_back.model.ParametresPlateforme;
import com.example.livraision_back.service.ParametresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parametres")
public class ParametresController {

    @Autowired
    private ParametresService service;

    @GetMapping
    public ParametresPlateforme getParams() {
        return service.get();
    }

    @PutMapping
    public ParametresPlateforme update(@RequestBody ParametresPlateforme params) {
        return service.save(params);
    }
}
