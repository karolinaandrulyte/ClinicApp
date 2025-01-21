package com.orion.clinics.services;

import com.orion.clinics.dtos.DocumentTypeDTO;
import com.orion.clinics.entities.DocumentTypeEntity;
import com.orion.clinics.mappers.DocumentTypeMapper;
import com.orion.clinics.repositories.DocumentTypeRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService {
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    public List<DocumentTypeDTO> findAll() {
        List<DocumentTypeEntity> documentTypes = documentTypeRepository.findAll();
        return documentTypes.stream()
                .map(documentTypeMapper::toDocumentTypeDTO)
                .toList();
    }

    public Optional<DocumentTypeDTO> findByType(String type) {
        DocumentTypeEntity documentType = documentTypeRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with type: " + type));
        return Optional.of(documentTypeMapper.toDocumentTypeDTO(documentType));
    }

    public DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO) {
        DocumentTypeEntity documentType = documentTypeMapper.toDocumentTypeEntity(documentTypeDTO);
        DocumentTypeEntity savedDocumentType = documentTypeRepository.save(documentType);
        return documentTypeMapper.toDocumentTypeDTO(savedDocumentType);
    }

    public void deleteByType(String type) {
        DocumentTypeEntity documentType = documentTypeRepository.findById(type)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with type: " + type));
        documentTypeRepository.delete(documentType);
    }

    public DocumentTypeDTO update(String type, DocumentTypeDTO documentTypeDTO) {
        DocumentTypeEntity existingDocumentType = documentTypeRepository.findById(type)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with type: " + type));
        existingDocumentType.setType(documentTypeDTO.getType());
        DocumentTypeEntity updatedDocumentType = documentTypeRepository.save(existingDocumentType);
        return documentTypeMapper.toDocumentTypeDTO(updatedDocumentType);
    }
}
