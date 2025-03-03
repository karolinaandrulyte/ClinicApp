package com.orion.clinics;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.RecordStatusMapper;
import com.orion.clinics.repositories.RecordStatusRepository;
import com.orion.clinics.services.RecordStatusService;
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
public class RecordStatusServiceTests {

    @Mock
    private RecordStatusRepository recordStatusRepository;

    @Mock
    private RecordStatusMapper recordStatusMapper;

    @InjectMocks
    private RecordStatusService recordStatusService;

    @Test
    void shouldSaveRecordStatus_whenValidDtoProvided() {
        RecordStatusDto recordStatusDto = new RecordStatusDto("employed");
        RecordStatusEntity recordStatusEntity = new RecordStatusEntity("employed");

        when(recordStatusMapper.toRecordStatusEntity(recordStatusDto)).thenReturn(recordStatusEntity);
        when(recordStatusRepository.save(recordStatusEntity)).thenReturn(recordStatusEntity);
        when(recordStatusMapper.toRecordStatusDto(recordStatusEntity)).thenReturn(recordStatusDto);

        RecordStatusDto result = recordStatusService.save(recordStatusDto);

        assertNotNull(result);
        assertEquals("employed", result.getStatus());
        verify(recordStatusRepository).save(recordStatusEntity);
    }

    @Test
    void shouldThrowException_whenRecordStatusAlreadyExists() {
        String status = "employed";
        RecordStatusDto recordStatusDto = new RecordStatusDto("employed");

        when(recordStatusRepository.existsByStatus(status)).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> recordStatusService.save(recordStatusDto));
        assertEquals("Record status already exists: employed", exception.getMessage());

        verify(recordStatusRepository).existsByStatus(status);
        verify(recordStatusRepository, never()).save(any());
    }

    @Test
    void shouldFindAllRecordStatuses_whenStatusesExist() {
        RecordStatusDto recordStatusDto = new RecordStatusDto("employed");
        RecordStatusEntity recordStatusEntity = new RecordStatusEntity("employed");

        when(recordStatusRepository.findAll()).thenReturn(List.of(recordStatusEntity));
        when(recordStatusMapper.toRecordStatusDto(recordStatusEntity)).thenReturn(recordStatusDto);

        List<RecordStatusDto> result = recordStatusService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(recordStatusRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoRecordStatusesExist() {
        when(recordStatusRepository.findAll()).thenReturn(Collections.emptyList());

        List<RecordStatusDto> result = recordStatusService.findAll();

        assertTrue(result.isEmpty());
        verify(recordStatusRepository).findAll();
    }

    @Test
    void shouldReturnTrue_whenRecordStatusExists() {
        String status = "employed";

        when(recordStatusRepository.existsByStatus(status)).thenReturn(true);

        boolean result = recordStatusService.existsByStatus(status);

        assertTrue(result);
        verify(recordStatusRepository).existsByStatus(status);
    }

    @Test
    void shouldReturnFalse_whenRecordStatusDoesNotExist() {
        String status = "unemployed";

        when(recordStatusRepository.existsByStatus(status)).thenReturn(false);

        boolean result = recordStatusService.existsByStatus(status);

        assertFalse(result);
        verify(recordStatusRepository).existsByStatus(status);
    }

    @Test
    void shouldDeleteRecordStatus_whenRecordStatusExists() {
        String status = "employed";
        RecordStatusEntity recordStatusEntity = new RecordStatusEntity(status);

        when(recordStatusRepository.findByStatus(status)).thenReturn(Optional.of(recordStatusEntity));

        recordStatusService.deleteByStatus(status);

        verify(recordStatusRepository).findByStatus(status);
        verify(recordStatusRepository).delete(recordStatusEntity);
    }

    @Test
    void shouldThrowException_whenRecordStatusToDeleteDoesNotExist() {
        String status = "unemployed";

        when(recordStatusRepository.findByStatus(status)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> recordStatusService.deleteByStatus(status));
        assertEquals("Record status not found with status: unemployed", exception.getMessage());

        verify(recordStatusRepository).findByStatus(status);
        verify(recordStatusRepository, never()).delete(any());
    }
}