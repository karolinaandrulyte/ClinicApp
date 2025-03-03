package com.orion.clinics;

import com.orion.clinics.dtos.CityDto;
import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CityRepository;
import com.orion.clinics.services.CityService;
import com.orion.clinics.services.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTests {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @Mock
    private CountryService countryService;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CityService cityService;

    @Test
    void shouldSaveCity_WhenCountryIsoCodeExists() {
        CountryDto countryDto = new CountryDto("LT", "Lithuania");
        CountryEntity countryEntity = new CountryEntity("LT", "Lithuania");
        CityDto cityDto = new CityDto(1L,"Vilnius", "LT");
        CityEntity cityEntity = new CityEntity(1L, "Vilnius", countryEntity);

        when(countryService.findByIsoCode("LT")).thenReturn(countryDto);
        when(countryMapper.toCountryEntity(countryDto)).thenReturn(countryEntity);
        when(cityMapper.toCityEntity(cityDto)).thenReturn(cityEntity);
        when(cityRepository.save(cityEntity)).thenReturn(cityEntity);
        when(cityMapper.toCityDto(cityEntity)).thenReturn(cityDto);

        CityDto result = cityService.save(cityDto);

        assertNotNull(result);
        assertEquals("Vilnius", result.getName());
        verify(cityRepository).save(cityEntity);
    }

    @Test
    void shouldThrowException_WhenCountryIsoCodeDoesNotExist() {
        CityDto cityDto = new CityDto(1L,"Vilnius", "LT");
        when(countryService.findByIsoCode("LT")).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> cityService.save(cityDto));
        assertEquals("Country with ISO code LT does not exist.", exception.getMessage());

        verify(countryService, times(1)).findByIsoCode("LT");
        verifyNoMoreInteractions(countryService);
    }

    @Test
    void shouldFindAllCities_whenCitiesExist() {
        CityDto cityDto = new CityDto(1L, "Vilnius", "LT");
        CityEntity cityEntity = new CityEntity(1L, "Vilnius", null);

        when(cityRepository.findAll()).thenReturn(List.of(cityEntity));
        when(cityMapper.toCityDto(cityEntity)).thenReturn(cityDto);

        List<CityDto> result = cityService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(cityRepository).findAll();
        verify(cityMapper, times(1)).toCityDto(cityEntity);
    }

    @Test
    void shouldReturnEmptyList_whenNoCitiesExist() {
        when(cityRepository.findAll()).thenReturn(List.of());

        List<CityDto> result = cityService.findAll();

        assertTrue(result.isEmpty());
        verify(cityRepository).findAll();
    }

    @Test
    void shouldFindCityById_whenCityExists() {
        CityDto cityDto = new CityDto(1L, "Vilnius", "LT");
        CityEntity cityEntity = new CityEntity(1L, "Vilnius", null);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(cityEntity));
        when(cityMapper.toCityDto(cityEntity)).thenReturn(cityDto);

        CityDto result = cityService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(cityRepository).findById(1L);
    }

    @Test
    void shouldThrowException_whenCityDoesNotExist() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> cityService.findById(1L));
        assertEquals("City not found with id: 1", exception.getMessage());

        verify(cityRepository).findById(1L);
    }

    @Test
    void shouldUpdateCity_whenCityExists() {
        CityDto cityDto = new CityDto(1L, "Vilnius", "LT");
        CityEntity cityEntity = new CityEntity(1L, "Vilnius", null);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(cityEntity));
        when(countryService.findByIsoCode("LT")).thenReturn(new CountryDto("LT", "Lithuania"));
        when(countryMapper.toCountryEntity(any(CountryDto.class))).thenReturn(new CountryEntity("LT", "Lithuania"));
        when(cityRepository.save(cityEntity)).thenReturn(cityEntity);
        when(cityMapper.toCityDto(cityEntity)).thenReturn(cityDto);

        CityDto result = cityService.update(1L, cityDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Vilnius", result.getName());
        assertEquals(cityDto.getName(), cityEntity.getName());
        assertEquals(cityDto.getCountryIsoCode(), cityEntity.getCountry().getIsoCode());
        verify(cityRepository).save(cityEntity);
    }

    @Test
    void shouldThrowException_whenCityToUpdateDoesNotExist() {
        CityDto cityDto = new CityDto(1L, "Vilnius", "LT");
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> cityService.update(1L, cityDto));
        assertEquals("City not found with id: 1", exception.getMessage());

        verify(cityRepository).findById(1L);
    }

    @Test
    void shouldDeleteCity_whenCityExists() {
        long cityId = 1L;

        when(cityRepository.existsById(cityId)).thenReturn(true);

        cityService.deleteById(cityId);

        verify(cityRepository, times(1)).existsById(cityId);
        verify(cityRepository, times(1)).deleteById(cityId);
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    void shouldThrowException_whenCityToDeleteDoesNotExist() {
        long cityId = 1L;

        when(cityRepository.existsById(cityId)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> cityService.deleteById(cityId));

        assertEquals("City not found with id: " + cityId, exception.getMessage());
        verify(cityRepository, times(1)).existsById(cityId);
        verify(cityRepository, never()).deleteById(anyLong());
    }

}