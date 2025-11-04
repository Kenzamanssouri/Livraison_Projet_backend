package com.example.livraision_back.service;

import com.example.livraision_back.model.PieceJointe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PieceJointeService {
    public PieceJointe saveFile(MultipartFile file, Long planActionId, String typePjPlanAction) throws IOException;
    List<PieceJointe> getByPlanActionId(Long planActionId, String typePjPlanAction);
    List<PieceJointe> getByType(String typePjPlanAction);
    void deletePieceJointe(Long id);
}
