package com.orion.clinics.services;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.entities.DocumentEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DocumentMapper;
import com.orion.clinics.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    public DocumentDto save(DocumentDto documentDto) {
        DocumentEntity document = documentMapper.toDocumentEntity(documentDto);
        DocumentEntity savedDocument = documentRepository.save(document);
        return documentMapper.toDocumentDto(savedDocument);
    }

    public List<DocumentDto> findAll() {
        List<DocumentEntity> documents = documentRepository.findAll();
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .toList();
    }

    public Optional<DocumentDto> findById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id);
        }
        Optional<DocumentEntity> document = documentRepository.findById(id);
        return document.map(documentMapper::toDocumentDto);
    }

    public List<DocumentDto> findDocumentsByClinicId(Long clinicId) {
        List<DocumentEntity> documents = documentRepository.findByClinicId(clinicId);
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .toList();
    }

    public DocumentDto update(Long id, DocumentDto documentDto) {
        if (!documentRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id);
        }
        DocumentEntity document = documentMapper.toDocumentEntity(documentDto);
        document.setId(id);
        DocumentEntity updatedDocument = documentRepository.save(document);
        return documentMapper.toDocumentDto(updatedDocument);
    }

    public void deleteById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}