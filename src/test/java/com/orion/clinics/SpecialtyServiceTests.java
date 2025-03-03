package com.orion.clinics;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.entities.SpecialtyEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.SpecialtyMapper;
import com.orion.clinics.repositories.SpecialtyRepository;
import com.orion.clinics.services.SpecialtyService;
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
public class SpecialtyServiceTests {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private SpecialtyMapper specialtyMapper;

    @InjectMocks
    private SpecialtyService specialtyService;

    @Test
    void shouldSaveSpecialty_whenValidDtoProvided() {
        SpecialtyDto specialtyDto = new SpecialtyDto("Cardiology");
        SpecialtyEntity specialtyEntity = new SpecialtyEntity("Cardiology");

        when(specialtyMapper.toSpecialtyEntity(specialtyDto)).thenReturn(specialtyEntity);
        when(specialtyRepository.save(specialtyEntity)).thenReturn(specialtyEntity);
        when(specialtyMapper.toSpecialtyDto(specialtyEntity)).thenReturn(specialtyDto);

        SpecialtyDto result = specialtyService.save(specialtyDto);

        assertNotNull(result);
        assertEquals("Cardiology", result.getName());
        verify(specialtyRepository).save(specialtyEntity);
    }

    @Test
    void shouldThrowException_whenSpecialtyAlreadyExists() {
        SpecialtyDto specialtyDto = new SpecialtyDto("Cardiology");
        SpecialtyEntity specialtyEntity = new SpecialtyEntity("Cardiology");

        when(specialtyRepository.existsById(specialtyDto.getName())).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> specialtyService.save(specialtyDto));
        assertEquals("Specialty with name Cardiology already exists.", exception.getMessage());

        verify(specialtyRepository).existsById(specialtyDto.getName());
        verify(specialtyRepository, never()).save(any());
    }

    @Test
    void shouldFindAllSpecialties_whenSpecialtiesExist() {
        SpecialtyDto specialtyDto = new SpecialtyDto("Cardiology");
        SpecialtyEntity specialtyEntity = new SpecialtyEntity("Cardiology");

        when(specialtyRepository.findAll()).thenReturn(List.of(specialtyEntity));
        when(specialtyMapper.toSpecialtyDto(specialtyEntity)).thenReturn(specialtyDto);

        List<SpecialtyDto> result = specialtyService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(specialtyRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_WhenNoSpecialtiesExist() {
        when(specialtyRepository.findAll()).thenReturn(Collections.emptyList());

        List<SpecialtyDto> result = specialtyService.findAll();

        assertTrue(result.isEmpty());
        verify(specialtyRepository).findAll();
    }

    @Test
    void shouldReturnTrue_whenSpecialtyExists() {
        String name = "Cardiology";

        when(specialtyRepository.existsById(name)).thenReturn(true);

        boolean result = specialtyService.existsByName(name);

        assertTrue(result);
        verify(specialtyRepository).existsById(name);
    }

    @Test
    void shouldReturnFalse_whenSpecialtyDoesNotExist() {
        String name = "Cardiology";

        when(specialtyRepository.existsById(name)).thenReturn(false);

        boolean result = specialtyService.existsByName(name);

        assertFalse(result);
        verify(specialtyRepository).existsById(name);
    }

    @Test
    void shouldDeleteSpecialty_whenSpecialtyExists() {
        String name = "Cardiology";
        SpecialtyEntity specialtyEntity = new SpecialtyEntity(name);

        when(specialtyRepository.findById(name)).thenReturn(Optional.of(specialtyEntity));

        specialtyService.deleteByName(name);

        verify(specialtyRepository).findById(name);
        verify(specialtyRepository).delete(specialtyEntity);
    }

    @Test
    void shouldThrowException_whenSpecialtyToDeleteDoesNotExist() {
        String name = "Cardiology";

        when(specialtyRepository.findById(name)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> specialtyService.deleteByName(name));
        assertEquals("Specialty not found with name: Cardiology", exception.getMessage());

        verify(specialtyRepository).findById(name);
    }
}