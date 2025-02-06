package com.orion.clinics.controllers;

import com.orion.clinics.dtos.DocumentTypeDto;
import com.orion.clinics.services.DocumentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document-types")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @PostMapping("/")
    public ResponseEntity<DocumentTypeDto> saveDocumentType(@RequestBody DocumentTypeDto documentTypeDto) {
        DocumentTypeDto savedDocumentType = documentTypeService.save(documentTypeDto);
        return new ResponseEntity<>(savedDocumentType, HttpStatus.CREATED);
    }

}