package com.orion.clinics.services;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CountryRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public List<CountryDto> findAll() {
        List<CountryEntity> countries = countryRepository.findAll();
        return countries.stream()
                .map(countryMapper::toCountryDto)
                .toList();
    }

    public Optional<CountryEntity> findByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode);  // Return an Optional<CountryEntity>
    }

    public CountryDto save(CountryDto countryDto) {
        CountryEntity country = countryMapper.toCountryEntity(countryDto);
        CountryEntity savedCountry = countryRepository.save(country);
        return countryMapper.toCountryDto(savedCountry);
    }

    public void deleteByIsoCode(String isoCode) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));
        countryRepository.delete(country);
    }

    public CountryDto update(String isoCode, CountryDto countryDto) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));
        country.setName(countryDto.getName());
        CountryEntity updatedCountry = countryRepository.save(country);
        return countryMapper.toCountryDto(updatedCountry);
    }

}