package com.orion.clinics;

import com.orion.clinics.dtos.ClinicStatusDto;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.ClinicStatusMapper;
import com.orion.clinics.repositories.ClinicStatusRepository;
import com.orion.clinics.services.ClinicStatusService;
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
public class ClinicStatusServiceTests {

    @Mock
    private ClinicStatusRepository clinicStatusRepository;

    @Mock
    private ClinicStatusMapper clinicStatusMapper;

    @InjectMocks
    private ClinicStatusService clinicStatusService;

    @Test
    void shouldSaveClinicStatus_whenValidDtoProvided() {
        ClinicStatusDto clinicStatusDto = new ClinicStatusDto("OPEN");
        ClinicStatusEntity clinicStatusEntity = new ClinicStatusEntity("OPEN");

        when(clinicStatusMapper.toClinicStatusEntity(clinicStatusDto)).thenReturn(clinicStatusEntity);
        when(clinicStatusRepository.save(clinicStatusEntity)).thenReturn(clinicStatusEntity);
        when(clinicStatusMapper.toClinicStatusDto(clinicStatusEntity)).thenReturn(clinicStatusDto);

        ClinicStatusDto result = clinicStatusService.save(clinicStatusDto);

        assertNotNull(result);
        assertEquals("OPEN", result.getStatus());
        verify(clinicStatusRepository).save(clinicStatusEntity);
    }

    @Test
    void shouldThrowException_whenClinicStatusAlreadyExists() {
        String status = "OPEN";
        ClinicStatusDto clinicStatusDto = new ClinicStatusDto(status);

        when(clinicStatusRepository.existsByStatus(status)).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> clinicStatusService.save(clinicStatusDto));

        assertEquals("Clinic status already exists: " + status, exception.getMessage());

        verify(clinicStatusRepository).existsByStatus(status);
        verify(clinicStatusRepository, never()).save(any());
    }

    @Test
    void shouldFindAllClinicStatuses_whenClinicStatusesExist() {
        ClinicStatusDto clinicStatusDto = new ClinicStatusDto("OPEN");
        ClinicStatusEntity clinicStatusEntity = new ClinicStatusEntity("OPEN");

        when(clinicStatusRepository.findAll()).thenReturn(List.of(clinicStatusEntity));
        when(clinicStatusMapper.toClinicStatusDto(clinicStatusEntity)).thenReturn(clinicStatusDto);

        List<ClinicStatusDto> result = clinicStatusService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(clinicStatusRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoClinicStatusesExist() {
        when(clinicStatusRepository.findAll()).thenReturn(Collections.emptyList());

        List<ClinicStatusDto> result = clinicStatusService.findAll();

        assertTrue(result.isEmpty());
        verify(clinicStatusRepository).findAll();
    }

    @Test
    void shouldReturnTrue_whenClinicStatusExists() {
        String status = "OPEN";

        when(clinicStatusRepository.existsByStatus(status)).thenReturn(true);

        boolean result = clinicStatusService.existsByStatus(status);

        assertTrue(result);
        verify(clinicStatusRepository).existsByStatus(status);
    }

    @Test
    void shouldReturnFalse_whenClinicStatusDoesNotExist() {
        String status = "OPEN";

        when(clinicStatusRepository.existsByStatus(status)).thenReturn(false);

        boolean result = clinicStatusService.existsByStatus(status);

        assertFalse(result);
        verify(clinicStatusRepository).existsByStatus(status);
    }

    @Test
    void shouldDeleteClinicStatus_whenClinicStatusExists() {
        String status = "OPEN";
        ClinicStatusEntity clinicStatusEntity = new ClinicStatusEntity(status);

        when(clinicStatusRepository.findByStatus(status)).thenReturn(Optional.of(clinicStatusEntity));

        clinicStatusService.deleteByStatus(status);

        verify(clinicStatusRepository).findByStatus(status);
        verify(clinicStatusRepository).delete(clinicStatusEntity);
    }

    @Test
    void shouldThrowException_whenClinicStatusToDeleteDoesNotExist() {

        String status = "OPEN";

        when(clinicStatusRepository.findByStatus(status)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> clinicStatusService.deleteByStatus(status));

        assertEquals("Clinic status not found with status: " + status, exception.getMessage());

        verify(clinicStatusRepository).findByStatus(status);
        verify(clinicStatusRepository, never()).delete(any());
    }
}