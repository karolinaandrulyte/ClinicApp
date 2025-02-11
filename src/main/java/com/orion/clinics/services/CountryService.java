package com.orion.clinics.services;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CountryRepository;
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

    public CountryDto save(CountryDto countryDto) {
        if (countryRepository.existsById(countryDto.getIsoCode())) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "Country with ISO code " + countryDto.getIsoCode() + " already exists.");
        }
        CountryEntity country = countryMapper.toCountryEntity(countryDto);
        CountryEntity savedCountry = countryRepository.save(country);
        return countryMapper.toCountryDto(savedCountry);
    }

    public List<CountryDto> findAll() {
        List<CountryEntity> countries = countryRepository.findAll();
        return countries.stream()
                .map(countryMapper::toCountryDto)
                .toList();
    }

    public Optional<CountryEntity> findByIsoCode(String isoCode) {
        return countryRepository.findById(isoCode);
    }

    public CountryDto update(String isoCode, CountryDto countryDto) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Country not found with ISO code: " + isoCode));
        country.setName(countryDto.getName());
        CountryEntity updatedCountry = countryRepository.save(country);
        return countryMapper.toCountryDto(updatedCountry);
    }

    public void deleteByIsoCode(String isoCode) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Country not found with ISO code: " + isoCode));
        countryRepository.delete(country);
    }

}