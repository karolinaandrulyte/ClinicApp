package com.orion.clinics.controllers;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.dtos.DocumentTypeDto;
import com.orion.clinics.services.DocumentService;
import com.orion.clinics.services.DocumentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentTypeService documentTypeService;

    public DocumentController(DocumentService documentService, DocumentTypeService documentTypeService) {
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
    }

    @PostMapping("/")
    public ResponseEntity<DocumentDto> saveDocument(@RequestBody DocumentDto documentDto) {
        DocumentDto savedDocument = documentService.save(documentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
    }

    @PostMapping("/types/")
    public ResponseEntity<DocumentTypeDto> saveDocumentType(@RequestBody DocumentTypeDto documentTypeDto) {
        DocumentTypeDto savedDocumentType = documentTypeService.save(documentTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumentType);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/types")
    public ResponseEntity<List<DocumentTypeDto>> getAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long id) {
        Optional<DocumentDto> document = documentService.findById(id);
        return document
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<DocumentDto>> getDocumentsByClinicId(@PathVariable Long id) {
        List<DocumentDto> documents = documentService.findDocumentsByClinicId(id);
        return ResponseEntity.ok(documents);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id, @RequestBody DocumentDto documentDto) {
        return ResponseEntity.ok(documentService.update(id, documentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/types/{type}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable String type) {
        documentTypeService.deleteByType(type);
        return ResponseEntity.noContent().build();
    }

}