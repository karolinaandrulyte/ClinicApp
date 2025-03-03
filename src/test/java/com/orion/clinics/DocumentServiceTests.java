package com.orion.clinics;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.entities.*;
import com.orion.clinics.enums.DocumentType;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DocumentMapper;
import com.orion.clinics.repositories.DocumentRepository;
import com.orion.clinics.services.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.orion.clinics.enums.DocumentType.CONSENT_FORM;
import static com.orion.clinics.enums.DocumentType.PAYMENT_RECEIPT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTests {
    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void shouldSaveDocument_whenValidDtoProvided() {
        Long id = 1L;
        DocumentDto documentDto = new DocumentDto(id, DocumentType.CONSENT_FORM, new byte[]{1, 2, 3}, 5L);
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.CONSENT_FORM, "Consent Form Description");
        ClinicEntity clinicEntity = new ClinicEntity(5L, "Clinic A", "Address", new CityEntity(1L, "Vilnius", new CountryEntity("Vilnius", "LT")));
        DocumentEntity documentEntity = new DocumentEntity(id, documentTypeEntity, new byte[]{1, 2, 3}, clinicEntity);

        when(documentMapper.toDocumentEntity(documentDto)).thenReturn(documentEntity);
        when(documentRepository.save(documentEntity)).thenReturn(documentEntity);
        when(documentMapper.toDocumentDto(documentEntity)).thenReturn(documentDto);

        DocumentDto result = documentService.save(documentDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(DocumentType.CONSENT_FORM, result.getType());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getContent());
        assertEquals(5L, result.getClinicId());
        verify(documentRepository).save(documentEntity);
    }

    @Test
    void shouldFindAllDocuments_whenDocumentsExist() {
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.LAB_REPORT, "Lab Report Description");
        ClinicEntity clinicEntity = new ClinicEntity(5L, "Clinic A", "Address", new CityEntity(1L, "Vilnius", new CountryEntity("Vilnius", "LT")));
        DocumentEntity documentEntity = new DocumentEntity(1L, documentTypeEntity, new byte[]{1, 2, 3}, clinicEntity);
        DocumentDto documentDto = new DocumentDto(1L, DocumentType.LAB_REPORT, new byte[]{1, 2, 3}, 5L);

        when(documentRepository.findAll()).thenReturn(List.of(documentEntity));
        when(documentMapper.toDocumentDto(documentEntity)).thenReturn(documentDto);

        List<DocumentDto> result = documentService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(DocumentType.LAB_REPORT, result.getFirst().getType());
        verify(documentRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoDocumentsExist() {
        when(documentRepository.findAll()).thenReturn(Collections.emptyList());

        List<DocumentDto> result = documentService.findAll();

        assertTrue(result.isEmpty());
        verify(documentRepository).findAll();
    }

    @Test
    void shouldFindDocumentById_whenDocumentExists() {
        Long id = 1L;
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.PRESCRIPTION, "Prescription Description");
        ClinicEntity clinicEntity = new ClinicEntity(5L, "Clinic A", "Address", new CityEntity(1L, "Vilnius", new CountryEntity("Vilnius", "LT")));
        DocumentEntity documentEntity = new DocumentEntity(id, documentTypeEntity, new byte[]{1, 2, 3}, clinicEntity);
        DocumentDto documentDto = new DocumentDto(id, DocumentType.PRESCRIPTION, new byte[]{1, 2, 3}, 5L);

        when(documentRepository.findById(id)).thenReturn(Optional.of(documentEntity));
        when(documentMapper.toDocumentDto(documentEntity)).thenReturn(documentDto);

        DocumentDto result = documentService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(DocumentType.PRESCRIPTION, result.getType());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getContent());
        assertEquals(5L, result.getClinicId());
        verify(documentRepository).findById(id);
    }

    @Test
    void shouldThrowException_whenDocumentNotFoundById() {
        Long id = 1L;

        when(documentRepository.findById(id)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> documentService.findById(id));
        assertEquals("Document not found with id: 1", exception.getMessage());

        verify(documentRepository).findById(id);
    }

    @Test
    void shouldFindDocumentsByClinicId_whenDocumentsExist() {
        Long id = 1L;
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(DocumentType.PRESCRIPTION, "Prescription Description");
        ClinicEntity clinicEntity = new ClinicEntity(5L, "Clinic A", "Address", new CityEntity(1L, "Vilnius", new CountryEntity("Vilnius", "LT")));
        DocumentEntity documentEntity = new DocumentEntity(id, documentTypeEntity, new byte[]{1, 2, 3}, clinicEntity);
        DocumentDto documentDto = new DocumentDto(id, DocumentType.PRESCRIPTION, new byte[]{1, 2, 3}, 5L);

        when(documentRepository.findById(id)).thenReturn(Optional.of(documentEntity));
        when(documentMapper.toDocumentDto(documentEntity)).thenReturn(documentDto);

        DocumentDto result = documentService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(DocumentType.PRESCRIPTION, result.getType());
        assertArrayEquals(new byte[]{1, 2, 3}, result.getContent());
        assertEquals(5L, result.getClinicId());

        verify(documentRepository).findById(id);
    }

    @Test
    void shouldUpdateDocument_whenDocumentExists() {
        Long id = 1L;
        DocumentDto updatedDocumentDto = new DocumentDto(id, PAYMENT_RECEIPT, new byte[]{4, 5, 6}, 5L);
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity(PAYMENT_RECEIPT, "Payment Receipt Description");
        ClinicEntity clinicEntity = new ClinicEntity(5L, "Clinic A", "Address", new CityEntity(1L, "Vilnius", new CountryEntity("LT", "Lithuania")));
        DocumentEntity existingDocument = new DocumentEntity(id, documentTypeEntity, new byte[]{1, 2, 3}, clinicEntity);
        DocumentEntity updatedDocumentEntity = new DocumentEntity(id, documentTypeEntity, new byte[]{4, 5, 6}, clinicEntity);

        when(documentRepository.existsById(id)).thenReturn(true);
        when(documentMapper.toDocumentEntity(updatedDocumentDto)).thenReturn(updatedDocumentEntity);
        when(documentRepository.save(updatedDocumentEntity)).thenReturn(updatedDocumentEntity);
        when(documentMapper.toDocumentDto(updatedDocumentEntity)).thenReturn(updatedDocumentDto);

        DocumentDto result = documentService.update(id, updatedDocumentDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(PAYMENT_RECEIPT, result.getType());
        assertArrayEquals(new byte[]{4, 5, 6}, result.getContent());
        assertEquals(5L, result.getClinicId());

        verify(documentRepository).existsById(id);
        verify(documentRepository).save(updatedDocumentEntity);
    }

    @Test
    void shouldThrowException_whenDocumentToUpdateDoesNotExist() {
        Long id = 1L;
        DocumentDto documentDto = new DocumentDto(id, CONSENT_FORM, new byte[]{1, 2, 3}, 5L);

        when(documentRepository.existsById(id)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> documentService.update(id, documentDto));
        assertEquals("Document not found with id: 1", exception.getMessage());

        verify(documentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteDocument_whenDocumentExists() {
        Long id = 1L;

        when(documentRepository.existsById(id)).thenReturn(true);

        documentService.deleteById(id);

        verify(documentRepository).existsById(id);
        verify(documentRepository).deleteById(id);
    }

    @Test
    void shouldThrowException_whenDocumentToDeleteDoesNotExist() {
        Long id = 1L;

        when(documentRepository.existsById(id)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> documentService.deleteById(id));
        assertEquals("Document not found with id: 1", exception.getMessage());

        verify(documentRepository, never()).deleteById(any());
    }
}