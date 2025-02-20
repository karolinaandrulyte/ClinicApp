package com.orion.clinics.services;

import com.orion.clinics.dtos.CityDto;
import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryMapper countryMapper;
    private final CountryService countryService;

    public CityService(CityRepository cityRepository, CityMapper cityMapper, CountryMapper countryMapper, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.countryMapper = countryMapper;
        this.countryService = countryService;
    }

    public CityDto save(CityDto cityDto) {
        CountryDto countryDto = countryService.findByIsoCode(cityDto.getCountryIsoCode());
        CountryEntity country = countryMapper.toCountryEntity(countryDto);

        CityEntity cityEntity = cityMapper.toCityEntity(cityDto);
        cityEntity.setCountry(country);

        CityEntity savedCity = cityRepository.save(cityEntity);

        return cityMapper.toCityDto(savedCity);
    }


    public List<CityDto> findAll() {
        List<CityEntity> cities = cityRepository.findAll();
        return cities.stream()
                .map(cityMapper::toCityDto)
                .toList();
    }

    public CityDto findById(Long id) {
        CityEntity cityEntity = cityRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "City not found with id: " + id));

        return cityMapper.toCityDto(cityEntity);
    }

    public CityDto update(Long id, CityDto cityDto) {
        CityEntity cityEntity = cityRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "City not found with id: " + id));

        CountryDto countryDto = countryService.findByIsoCode(cityDto.getCountryIsoCode());

        cityEntity.setName(cityDto.getName());
        cityEntity.setCountry(countryMapper.toCountryEntity(countryDto));

        CityEntity updatedCity = cityRepository.save(cityEntity);

        return cityMapper.toCityDto(updatedCity);
    }

    public void deleteById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }
}