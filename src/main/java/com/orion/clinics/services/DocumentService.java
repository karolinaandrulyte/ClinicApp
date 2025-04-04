package com.orion.clinics.services;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.entities.DocumentEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DocumentMapper;
import com.orion.clinics.repositories.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public DocumentDto save(DocumentDto documentDto) {
        DocumentEntity document = documentMapper.toDocumentEntity(documentDto);
        if (documentRepository.existsByContent(document.getContent())) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "A document with the same content already exists.");
        }
        DocumentEntity savedDocument = documentRepository.save(document);
        return documentMapper.toDocumentDto(savedDocument);
    }

    @Transactional
    public List<DocumentDto> findAll() {
        List<DocumentEntity> documents = documentRepository.findAll();
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .toList();
    }

    @Transactional
    public DocumentDto findById(Long id) {
        DocumentEntity document = documentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id));
        return documentMapper.toDocumentDto(document);
    }

    @Transactional
    public List<DocumentDto> findDocumentsByClinicId(Long clinicId) {
        List<DocumentEntity> documents = documentRepository.findByClinicId(clinicId);
        return documents.stream()
                .map(documentMapper::toDocumentDto)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public DocumentDto update(Long id, DocumentDto documentDto) {
        if (!documentRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id);
        }
        DocumentEntity document = documentMapper.toDocumentEntity(documentDto);
        document.setId(id);
//        DocumentEntity updatedDocument = documentRepository.save(document); since transactional, no need to save
        return documentMapper.toDocumentDto(document);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}