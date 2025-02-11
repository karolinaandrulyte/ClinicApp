package com.orion.clinics.services;

import com.orion.clinics.dtos.DocumentTypeDto;
import com.orion.clinics.entities.DocumentTypeEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DocumentTypeMapper;
import com.orion.clinics.repositories.DocumentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    public DocumentTypeDto save(DocumentTypeDto documentTypeDto) {
        DocumentTypeEntity documentType = documentTypeMapper.toDocumentTypeEntity(documentTypeDto);
        DocumentTypeEntity savedDocumentType = documentTypeRepository.save(documentType);
        return documentTypeMapper.toDocumentTypeDto(savedDocumentType);
    }

    public List<DocumentTypeDto> findAll() {
        List<DocumentTypeEntity> documentTypes = documentTypeRepository.findAll();
        return documentTypes.stream()
                .map(documentTypeMapper::toDocumentTypeDto)
                .toList();
    }

    public boolean existsByType(String type) {
        return documentTypeRepository.existsById(type);
    }

    public void deleteByType(String type) {
        DocumentTypeEntity documentType = documentTypeRepository.findById(type)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Document type not found with type: " + type));
        documentTypeRepository.delete(documentType);
    }
}