package com.example.livraision_back.controller;

import com.example.livraision_back.model.CategorieProduit;
import com.example.livraision_back.repository.CategorieProduitRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/download")
public class ReportController {

    private final CategorieProduitRepository categorieRepository;

    public ReportController(CategorieProduitRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadTemplate() throws Exception {

        // 1️⃣ Créer le fichier Excel
        Workbook workbook = new XSSFWorkbook();

        // ============================
        // 2️⃣ FEUILLE PRODUITS
        // ============================
        Sheet produitsSheet = workbook.createSheet("Produits");

        Row header = produitsSheet.createRow(0);
        header.createCell(0).setCellValue("nom");
        header.createCell(1).setCellValue("description");
        header.createCell(2).setCellValue("prix");
        header.createCell(3).setCellValue("categorie");
        header.createCell(4).setCellValue("image");

        // ============================
        // 🔢 VALIDATION PRIX NUMÉRIQUE
        // ============================
        DataValidationHelper produitsHelper = produitsSheet.getDataValidationHelper();

        DataValidationConstraint priceConstraint =
            produitsHelper.createDecimalConstraint(
                DataValidationConstraint.OperatorType.GREATER_OR_EQUAL,
                "0",
                null
            );

        CellRangeAddressList priceRange =
            new CellRangeAddressList(1, 1000, 2, 2); // colonne prix

        DataValidation priceValidation =
            produitsHelper.createValidation(priceConstraint, priceRange);

        priceValidation.setShowErrorBox(true);
        priceValidation.createErrorBox(
            "Valeur invalide",
            "Le champ prix doit être un nombre (ex: 10 ou 12.5)"
        );

        produitsSheet.addValidationData(priceValidation);

        // ============================
        // 3️⃣ FEUILLE CATEGORIES
        // ============================
        Sheet categoriesSheet = workbook.createSheet("Categories");

        Row catHeader = categoriesSheet.createRow(0);
        catHeader.createCell(0).setCellValue("id");
        catHeader.createCell(1).setCellValue("nom");

        List<CategorieProduit> categories = categorieRepository.findAll();

        int rowIdx = 1;
        for (CategorieProduit c : categories) {
            Row row = categoriesSheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(c.getId());
            row.createCell(1).setCellValue(c.getNom());
        }

        // ============================
        // 4️⃣ SELECT (DROPDOWN) CATEGORIE
        // ============================
        DataValidationHelper catHelper = produitsSheet.getDataValidationHelper();

        DataValidationConstraint catConstraint =
            catHelper.createFormulaListConstraint(
                "Categories!$B$2:$B$" + rowIdx
            );

        CellRangeAddressList catRange =
            new CellRangeAddressList(1, 1000, 3, 3);

        DataValidation catValidation =
            catHelper.createValidation(catConstraint, catRange);

        catValidation.setShowErrorBox(true);
        produitsSheet.addValidationData(catValidation);

        // ============================
        // 5️⃣ CACHER FEUILLE CATEGORIES
        // ============================
        workbook.setSheetHidden(
            workbook.getSheetIndex(categoriesSheet),
            true
        );

        // ============================
        // 6️⃣ EXPORT
        // ============================
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=produits_template.xlsx"
            )
            .contentType(
                MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                )
            )
            .body(out.toByteArray());
    }
}
