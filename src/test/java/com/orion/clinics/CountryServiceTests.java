package com.orion.clinics;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CountryRepository;
import com.orion.clinics.services.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTests {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    @Test
    void shouldSaveCountry_WhenIsoCodeIsUnique() {
        CountryDto countryDto = new CountryDto("LT", "Lithuania");
        CountryEntity countryEntity = new CountryEntity("LT", "Lithuania");

        when(countryRepository.existsById("LT")).thenReturn(false);
        when(countryMapper.toCountryEntity(countryDto)).thenReturn(countryEntity);
        when(countryRepository.save(countryEntity)).thenReturn(countryEntity);
        when(countryMapper.toCountryDto(countryEntity)).thenReturn(countryDto);

        CountryDto savedCountry = countryService.save(countryDto);

        assertNotNull(savedCountry);
        assertEquals("LT", savedCountry.getIsoCode());
        verify(countryRepository, times(1)).save(countryEntity);
        verifyNoMoreInteractions(countryRepository, countryMapper);
    }

    @Test
    void shouldThrowException_WhenCountryIsoCodeAlreadyExists() {
        CountryDto countryDto = new CountryDto("LT", "Lithuania");
        when(countryRepository.existsById("LT")).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> countryService.save(countryDto));
        assertEquals("Country with ISO code LT already exists.", exception.getMessage());

        verify(countryRepository, times(1)).existsById("LT");
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void shouldReturnAllCountries_WhenCountriesExist() {
        List<CountryEntity> countryEntities = List.of(new CountryEntity("LT", "Lithuania"), new CountryEntity("LV", "Latvia"));
        List<CountryDto> expectedDtos = List.of(new CountryDto("LT", "Lithuania"), new CountryDto("LV", "Latvia"));

        when(countryRepository.findAll()).thenReturn(countryEntities);
        when(countryMapper.toCountryDto(any(CountryEntity.class))).thenAnswer(invocation -> {
            CountryEntity entity = invocation.getArgument(0);
            return new CountryDto(entity.getIsoCode(), entity.getName());
        });

        List<CountryDto> result = countryService.findAll();

        assertEquals(2, result.size());
        assertEquals("LT", result.get(0).getIsoCode());
        assertEquals("LV", result.get(1).getIsoCode());

        verify(countryRepository, times(1)).findAll();
        verify(countryMapper, times(2)).toCountryDto(any(CountryEntity.class));
        verifyNoMoreInteractions(countryRepository, countryMapper);
    }

    @Test
    void shouldReturnEmptyList_WhenNoCountriesExist() {
        when(countryRepository.findAll()).thenReturn(List.of());

        List<CountryDto> result = countryService.findAll();

        assertTrue(result.isEmpty());

        verify(countryRepository, times(1)).findAll();
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void shouldReturnCountry_WhenIsoCodeIsValid() {
        CountryEntity countryEntity = new CountryEntity("LT", "Lithuania");
        CountryDto countryDto = new CountryDto("LT", "Lithuania");

        when(countryRepository.findById("LT")).thenReturn(Optional.of(countryEntity));
        when(countryMapper.toCountryDto(countryEntity)).thenReturn(countryDto);

        CountryDto  result = countryService.findByIsoCode("LT");

        assertNotNull(result);
        assertEquals("LT", result.getIsoCode());
        assertEquals("Lithuania", result.getName());

        verify(countryRepository, times(1)).findById("LT");
        verify(countryMapper, times(1)).toCountryDto(countryEntity);
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void shouldThrowException_WhenIsoCodeDoesNotExist() {
        when(countryRepository.findById("XX")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> countryService.findByIsoCode("XX"));
        assertEquals("Country not found with ISO code: XX", exception.getMessage());

        verify(countryRepository, times(1)).findById("XX");
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void shouldUpdateCountry_WhenIsoCodeIsValid() {
        CountryEntity existingCountry = new CountryEntity("LT", "Lithuania");
        CountryDto updatedCountryDto = new CountryDto("LT", "Updated Lithuania");

        when(countryRepository.findById("LT")).thenReturn(Optional.of(existingCountry));
        when(countryRepository.save(any(CountryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(countryMapper.toCountryDto(any(CountryEntity.class))).thenAnswer(invocation -> new CountryDto("LT", "Updated Lithuania"));

        CountryDto result = countryService.update("LT", updatedCountryDto);

        assertNotNull(result);
        assertEquals("LT", result.getIsoCode());
        assertEquals("Updated Lithuania", result.getName());

        verify(countryRepository, times(1)).findById("LT");
        verify(countryRepository, times(1)).save(any(CountryEntity.class));
        verify(countryMapper, times(1)).toCountryDto(any(CountryEntity.class));
        verifyNoMoreInteractions(countryRepository, countryMapper);
    }

    @Test
    void shouldThrowException_WhenUpdatingCountryWithNonexistentIsoCode() {
        CountryDto countryDto = new CountryDto("XX", "Nonexistent");
        when(countryRepository.findById("XX")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> countryService.update("XX", countryDto));
        assertEquals("Country not found with ISO code: XX", exception.getMessage());

        verify(countryRepository, times(1)).findById("XX");
        verifyNoMoreInteractions(countryRepository, countryMapper);
    }

    @Test
    void shouldDeleteCountry_WhenIsoCodeExists() {
        CountryEntity countryEntity = new CountryEntity("LT", "Lithuania");
        when(countryRepository.findById("LT")).thenReturn(Optional.of(countryEntity));

        countryService.deleteByIsoCode("LT");

        verify(countryRepository, times(1)).delete(countryEntity);
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void shouldThrowException_WhenDeletingCountryWithNonexistentIsoCode () {
        when(countryRepository.findById("XX")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> countryService.deleteByIsoCode("XX"));
        assertEquals("Country not found with ISO code: XX", exception.getMessage());

        verify(countryRepository, times(1)).findById("XX");
        verifyNoMoreInteractions(countryRepository);
    }

}