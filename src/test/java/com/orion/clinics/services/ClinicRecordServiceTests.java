package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicRecordDto;
import com.orion.clinics.entities.ClinicEntity;
import com.orion.clinics.entities.ClinicRecordEntity;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.ClinicRecordMapper;
import com.orion.clinics.repositories.ClinicRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClinicRecordServiceTests {

    @Mock
    private ClinicRecordRepository clinicRecordRepository;

    @Mock
    private ClinicRecordMapper clinicRecordMapper;

    @InjectMocks
    private ClinicRecordService clinicRecordService;

    @Test
    void shouldSaveClinicRecord_whenValidDtoProvided() {
        ClinicRecordDto clinicRecordDto = new ClinicRecordDto(null, "open", LocalDateTime.now(), 1L);
        ClinicRecordEntity clinicRecordEntity = new ClinicRecordEntity(null, new ClinicStatusEntity("open"), clinicRecordDto.getUpdated(), new ClinicEntity(1L, "Clinic A", "Address", null));
        ClinicRecordEntity savedEntity = new ClinicRecordEntity(1L, clinicRecordEntity.getStatus(), clinicRecordEntity.getUpdated(), clinicRecordEntity.getClinic());
        ClinicRecordDto savedDto = new ClinicRecordDto(1L, "open", savedEntity.getUpdated(), 1L);

        when(clinicRecordMapper.toClinicRecordEntity(clinicRecordDto)).thenReturn(clinicRecordEntity);
        when(clinicRecordRepository.save(clinicRecordEntity)).thenReturn(savedEntity);
        when(clinicRecordMapper.toClinicRecordDto(savedEntity)).thenReturn(savedDto);

        ClinicRecordDto result = clinicRecordService.save(clinicRecordDto);

        assertNotNull(result);
        assertEquals(savedDto, result);
        verify(clinicRecordRepository).save(clinicRecordEntity);
    }

    @Test
    void shouldFindAllClinicRecords_whenRecordsExist() {
        List<ClinicRecordEntity> entities = List.of(
                new ClinicRecordEntity(1L, new ClinicStatusEntity("open"), LocalDateTime.now(), new ClinicEntity(1L, "Clinic A", "Address", null)),
                new ClinicRecordEntity(2L, new ClinicStatusEntity("closed"), LocalDateTime.now(), new ClinicEntity(2L, "Clinic B", "Address", null))
        );

        List<ClinicRecordDto> dtos = List.of(
                new ClinicRecordDto(1L, "open", entities.get(0).getUpdated(), 1L),
                new ClinicRecordDto(2L, "closed", entities.get(1).getUpdated(), 2L)
        );

        when(clinicRecordRepository.findAll()).thenReturn(entities);
        when(clinicRecordMapper.toClinicRecordDto(any())).thenReturn(dtos.get(0), dtos.get(1));

        List<ClinicRecordDto> result = clinicRecordService.findAll();

        assertNotNull(result);
        assertEquals(dtos, result);
        verify(clinicRecordRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoClinicRecordsExist() {
        when(clinicRecordRepository.findAll()).thenReturn(List.of());

        List<ClinicRecordDto> result = clinicRecordService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clinicRecordRepository).findAll();
    }

    @Test
    void shouldFindClinicRecordById_whenRecordExists() {
        Long id = 1L;
        ClinicRecordEntity entity = new ClinicRecordEntity(id, new ClinicStatusEntity("open"), LocalDateTime.now(), new ClinicEntity(1L, "Clinic A", "Address", null));
        ClinicRecordDto dto = new ClinicRecordDto(id, "open", entity.getUpdated(), 1L);

        when(clinicRecordRepository.findById(id)).thenReturn(Optional.of(entity));
        when(clinicRecordMapper.toClinicRecordDto(entity)).thenReturn(dto);

        ClinicRecordDto result = clinicRecordService.findById(id);

        assertNotNull(result);
        assertEquals(dto, result);
        verify(clinicRecordRepository).findById(id);
    }

    @Test
    void shouldThrowException_whenClinicRecordNotFoundById() {
        Long id = 1L;

        when(clinicRecordRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ApiException.class, () -> clinicRecordService.findById(id));
        assertTrue(exception.getMessage().contains("Clinic record not found with id: " + id));

        verify(clinicRecordRepository).findById(id);
    }

    @Test
    void shouldReturnEmptyList_whenNoClinicRecordsExistForClinicId() {
        Long clinicId = 1L;

        when(clinicRecordRepository.findByClinicId(clinicId)).thenReturn(List.of());

        List<ClinicRecordDto> result = clinicRecordService.findByClinicId(clinicId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clinicRecordRepository).findByClinicId(clinicId);
    }

    @Test
    void shouldUpdateClinicRecord_whenRecordExists() {
        Long id = 1L;
        ClinicRecordDto updatedDto = new ClinicRecordDto(id, "closed", LocalDateTime.now(), 2L);
        ClinicRecordEntity existingEntity = new ClinicRecordEntity(id, new ClinicStatusEntity("open"), LocalDateTime.now(), new ClinicEntity(1L, "Clinic A", "Address", null));
        ClinicRecordEntity updatedEntity = new ClinicRecordEntity(id, new ClinicStatusEntity("closed"), updatedDto.getUpdated(), new ClinicEntity(2L, "Clinic B", "Address", null));

        when(clinicRecordRepository.existsById(id)).thenReturn(true);
        when(clinicRecordRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(clinicRecordMapper.toClinicRecordEntity(updatedDto)).thenReturn(updatedEntity);
        when(clinicRecordRepository.save(existingEntity)).thenReturn(updatedEntity);
        when(clinicRecordMapper.toClinicRecordDto(updatedEntity)).thenReturn(updatedDto);

        ClinicRecordDto result = clinicRecordService.update(updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto, result);
        verify(clinicRecordRepository).save(existingEntity);
    }

    @Test
    void shouldThrowException_whenUpdatingNonexistentClinicRecord() {
        ClinicRecordDto clinicRecordDto = new ClinicRecordDto(1L, "open", LocalDateTime.now(), 1L);
        when(clinicRecordRepository.existsById(clinicRecordDto.getId())).thenReturn(false);

        Exception exception = assertThrows(ApiException.class, () -> clinicRecordService.update(clinicRecordDto));
        assertTrue(exception.getMessage().contains("Clinic record not found with id: " + clinicRecordDto.getId()));

        verify(clinicRecordRepository).existsById(clinicRecordDto.getId());
        verify(clinicRecordRepository, never()).save(any());
    }

    @Test
    void shouldDeleteClinicRecord_whenRecordExists() {
        Long id = 1L;

        when(clinicRecordRepository.existsById(id)).thenReturn(true);

        clinicRecordService.deleteById(id);

        verify(clinicRecordRepository).existsById(id);
        verify(clinicRecordRepository).deleteById(id);
    }

    @Test
    void shouldThrowException_whenDeletingNonexistentClinicRecord() {
        Long id = 1L;

        when(clinicRecordRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(ApiException.class, () -> clinicRecordService.deleteById(id));
        assertTrue(exception.getMessage().contains("Clinic record not found with id: " + id));

        verify(clinicRecordRepository).existsById(id);
        verify(clinicRecordRepository, never()).deleteById(anyLong());
    }
}