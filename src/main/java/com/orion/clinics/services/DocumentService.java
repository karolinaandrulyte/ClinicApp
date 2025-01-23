package com.orion.clinics.services;

import com.orion.clinics.dtos.DocumentDTO;
import com.orion.clinics.entities.DocumentEntity;
import com.orion.clinics.mappers.DocumentMapper;
import com.orion.clinics.repositories.DocumentRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

    public List<DocumentDTO> findAll() {
        List<DocumentEntity> documents = documentRepository.findAll();
        return documents.stream()
                .map(documentMapper::toDocumentDTO)
                .toList();
    }

    public Optional<DocumentDTO> findById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        Optional<DocumentEntity> document = documentRepository.findById(id);
        return document.map(documentMapper::toDocumentDTO);
    }

    public DocumentDTO save(DocumentDTO documentDTO) {
        DocumentEntity document = documentMapper.toDocumentEntity(documentDTO);
        DocumentEntity savedDocument = documentRepository.save(document);
        return documentMapper.toDocumentDTO(savedDocument);
    }

    public DocumentDTO update(Long id, DocumentDTO documentDTO) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        DocumentEntity document = documentMapper.toDocumentEntity(documentDTO);
        document.setId(id);
        DocumentEntity updatedDocument = documentRepository.save(document);
        return documentMapper.toDocumentDTO(updatedDocument);
    }

    public void deleteById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}
