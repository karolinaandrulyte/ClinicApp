package com.orion.clinics.services;

import com.orion.clinics.dtos.CityDto;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CityRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        CountryEntity country = countryService.findByIsoCode(cityDto.getCountryIsoCode())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + cityDto.getCountryIsoCode()));

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

    public Optional<CityEntity> findById(Long id) {
        return cityRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }

    public CityDto update(Long id, CityDto cityDto) {
        CityEntity cityEntity = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));

        CountryEntity country = countryService.findByIsoCode(cityDto.getCountryIsoCode())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + cityDto.getCountryIsoCode()));

        cityEntity.setName(cityDto.getName());
        cityEntity.setCountry(country);

        CityEntity updatedCity = cityRepository.save(cityEntity);

        return cityMapper.toCityDto(updatedCity);
    }
}