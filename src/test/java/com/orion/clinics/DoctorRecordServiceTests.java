package com.orion.clinics;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DoctorRecordMapper;
import com.orion.clinics.repositories.DoctorRecordRepository;
import com.orion.clinics.repositories.DoctorRepository;
import com.orion.clinics.repositories.RecordStatusRepository;
import com.orion.clinics.services.DoctorRecordService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorRecordServiceTests {

    @Mock
    private DoctorRecordRepository doctorRecordRepository;

    @Mock
    private DoctorRecordMapper doctorRecordMapper;

    @Mock
    private RecordStatusRepository recordStatusRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorRecordService doctorRecordService;

    @Test
    void shouldSaveDoctorRecord_whenValidDtoProvided() {
        RecordStatusEntity statusEntity = new RecordStatusEntity("ACTIVE");
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setId(1L);
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto(1L, LocalDateTime.now(), "ACTIVE", 1L);
        DoctorRecordEntity doctorRecordEntity = new DoctorRecordEntity();
        doctorRecordEntity.setId(1L);
        doctorRecordEntity.setUpdated(LocalDateTime.now());
        doctorRecordEntity.setStatus(statusEntity);
        doctorRecordEntity.setDoctor(doctorEntity);

        when(recordStatusRepository.findByStatus("ACTIVE")).thenReturn(Optional.of(statusEntity));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctorEntity));
        when(doctorRecordRepository.save(any())).thenReturn(doctorRecordEntity);

        DoctorRecordDto result = doctorRecordService.save(doctorRecordDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(doctorRecordRepository).save(any());
    }

    @Test
    void shouldThrowException_whenSavingWithInvalidStatus() {
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto(1L, LocalDateTime.now(), "ACTIVE", 1L);
        when(recordStatusRepository.findByStatus("ACTIVE")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> doctorRecordService.save(doctorRecordDto));
        assertTrue(exception.getMessage().contains("Status not found"));
    }

    @Test
    void shouldFindAllDoctorRecords_whenRecordsExist() {
        DoctorRecordEntity doctorRecordEntity = new DoctorRecordEntity();
        doctorRecordEntity.setId(1L);
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto(1L, LocalDateTime.now(), "ACTIVE", 1L);

        when(doctorRecordRepository.findAll()).thenReturn(List.of(doctorRecordEntity));
        when(doctorRecordMapper.toDoctorRecordDto(doctorRecordEntity)).thenReturn(doctorRecordDto);

        List<DoctorRecordDto> results = doctorRecordService.findAll();

        assertEquals(1, results.size());
        assertEquals(1L, results.getFirst().getId());
    }

    @Test
    void shouldFindDoctorRecordById_whenRecordExists() {
        DoctorRecordEntity doctorRecordEntity = new DoctorRecordEntity();
        doctorRecordEntity.setId(1L);
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto(1L, LocalDateTime.now(), "ACTIVE", 1L);

        when(doctorRecordRepository.findById(1L)).thenReturn(Optional.of(doctorRecordEntity));
        when(doctorRecordMapper.toDoctorRecordDto(doctorRecordEntity)).thenReturn(doctorRecordDto);

        DoctorRecordDto result = doctorRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowException_whenFindingNonexistentDoctorRecord() {
        when(doctorRecordRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> doctorRecordService.findById(1L));
        assertTrue(exception.getMessage().contains("Doctor record not found"));
    }

//    @Test
//    void shouldUpdateDoctorRecord_whenValidDtoProvided() {
//        RecordStatusEntity statusEntity = new RecordStatusEntity("ACTIVE");
//        DoctorEntity doctorEntity = new DoctorEntity();
//        doctorEntity.setId(1L);
//
//        DoctorRecordEntity existingRecord = new DoctorRecordEntity();
//        existingRecord.setId(1L);
//        existingRecord.setUpdated(LocalDateTime.now().minusDays(1));
//        existingRecord.setStatus(statusEntity);
//        existingRecord.setDoctor(doctorEntity);
//
//        DoctorRecordDto updateDto = new DoctorRecordDto(1L, LocalDateTime.now(), "ACTIVE", 1L);
//
//        when(doctorRecordRepository.existsById(1L)).thenReturn(true);
//        when(doctorRecordRepository.findById(1L)).thenReturn(Optional.of(existingRecord));
//        when(recordStatusRepository.findByStatus("ACTIVE")).thenReturn(Optional.of(statusEntity));
//        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctorEntity));
//        when(doctorRecordRepository.save(any())).thenReturn(existingRecord);
//
//        DoctorRecordDto result = doctorRecordService.update(updateDto);
//
//        assertNotNull(result, "Result should not be null");
//        assertEquals(1L, result.getId());
//        assertEquals("ACTIVE", result.getStatus());
//        verify(doctorRecordRepository).save(any());
//    }

    @Test
    void shouldThrowException_whenUpdatingNonexistentDoctorRecord() {
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto(1L, LocalDateTime.now(), "UPDATED", 1L);
        when(doctorRecordRepository.existsById(1L)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> doctorRecordService.update(doctorRecordDto));
        assertTrue(exception.getMessage().contains("Doctor record not found"));
    }

    @Test
    void shouldDeleteDoctorRecord_whenRecordExists() {
        when(doctorRecordRepository.existsById(1L)).thenReturn(true);

        doctorRecordService.deleteById(1L);

        verify(doctorRecordRepository).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonexistentDoctorRecord() {
        when(doctorRecordRepository.existsById(1L)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> doctorRecordService.deleteById(1L));
        assertTrue(exception.getMessage().contains("Doctor record not found"));
    }

}