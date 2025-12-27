package com.example.livraision_back.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Sauvegarde un fichier et retourne son URL publique
     */
    String save(MultipartFile file);
}
