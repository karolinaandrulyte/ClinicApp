package com.orion.clinics;

import com.orion.clinics.dtos.DocumentTypeDto;
import com.orion.clinics.entities.DocumentTypeEntity;
import com.orion.clinics.enums.DocumentType;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DocumentTypeMapper;
import com.orion.clinics.repositories.DocumentTypeRepository;
import com.orion.clinics.services.DocumentTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentTypeServiceTests {

    @Mock
    private DocumentTypeRepository documentTypeRepository;

    @Mock
    private DocumentTypeMapper documentTypeMapper;

    @InjectMocks
    private DocumentTypeService documentTypeService;

    @Test
    void shouldSaveDocumentType_whenValidDtoProvided() {
        String type = "PRESCRIPTION";
        DocumentTypeDto documentTypeDto = new DocumentTypeDto(type, "Prescription");
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.PRESCRIPTION, "Prescription");

        when(documentTypeMapper.toDocumentTypeEntity(documentTypeDto)).thenReturn(documentTypeEntity);
        when(documentTypeRepository.save(documentTypeEntity)).thenReturn(documentTypeEntity);
        when(documentTypeMapper.toDocumentTypeDto(documentTypeEntity)).thenReturn(documentTypeDto);

        DocumentTypeDto result = documentTypeService.save(documentTypeDto);

        assertNotNull(result);
        assertEquals(type, result.getType());
        assertEquals("Prescription", result.getDescription());
        verify(documentTypeRepository).save(documentTypeEntity);
    }

    @Test
    void shouldFindAllDocumentTypes_whenDocumentTypesExist() {
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.PRESCRIPTION, "Prescription");
        DocumentTypeDto documentTypeDto = new DocumentTypeDto("PRESCRIPTION", "Prescription");

        when(documentTypeRepository.findAll()).thenReturn(List.of(documentTypeEntity));
        when(documentTypeMapper.toDocumentTypeDto(documentTypeEntity)).thenReturn(documentTypeDto);

        List<DocumentTypeDto> result = documentTypeService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("PRESCRIPTION", result.getFirst().getType());
        assertEquals("Prescription", result.getFirst().getDescription());
        verify(documentTypeRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoDocumentTypesExist() {
        when(documentTypeRepository.findAll()).thenReturn(Collections.emptyList());

        List<DocumentTypeDto> result = documentTypeService.findAll();

        assertTrue(result.isEmpty());
        verify(documentTypeRepository).findAll();
    }

    @Test
    void shouldReturnTrue_whenDocumentTypeExists() {
        DocumentType type = DocumentType.PRESCRIPTION;

        when(documentTypeRepository.existsById(type.name())).thenReturn(true);

        boolean result = documentTypeService.existsByType(type.name());

        assertTrue(result);
        verify(documentTypeRepository).existsById(type.name());
    }

    @Test
    void shouldReturnFalse_whenDocumentTypeDoesNotExist() {
        DocumentType type = DocumentType.PRESCRIPTION;

        when(documentTypeRepository.existsById(type.name())).thenReturn(false);

        boolean result = documentTypeService.existsByType(type.name());

        assertFalse(result);
        verify(documentTypeRepository).existsById(type.name());
    }

    @Test
    void shouldDeleteDocumentType_whenDocumentTypeExists() {
        DocumentType type = DocumentType.PRESCRIPTION;
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(type, "Prescription");

        when(documentTypeRepository.findById(type.name())).thenReturn(Optional.of(documentTypeEntity));

        documentTypeService.deleteByType(type.name());

        verify(documentTypeRepository).findById(type.name());
        verify(documentTypeRepository).delete(documentTypeEntity);
    }

    @Test
    void shouldThrowException_whenDocumentTypeToDeleteDoesNotExist() {
        DocumentType type = DocumentType.PRESCRIPTION;

        when(documentTypeRepository.findById(type.name())).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> documentTypeService.deleteByType(type.name()));
        assertEquals("Document type not found with type: PRESCRIPTION", exception.getMessage());

        verify(documentTypeRepository).findById(type.name());
        verify(documentTypeRepository, never()).delete(any());
    }
}