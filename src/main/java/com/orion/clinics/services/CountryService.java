package com.orion.clinics.services;

import com.orion.clinics.dtos.CountryDTO;
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

    public List<CountryDTO> findAll() {
        List<CountryEntity> countries = countryRepository.findAll();
        return countries.stream()
                .map(countryMapper::toCountryDTO)
                .toList();
    }

    public Optional<CountryDTO> findByIsoCode(String isoCode) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));
        return Optional.of(countryMapper.toCountryDTO(country));
    }

    public CountryDTO save(CountryDTO countryDTO) {
        CountryEntity country = countryMapper.toCountryEntity(countryDTO);
        CountryEntity savedCountry = countryRepository.save(country);
        return countryMapper.toCountryDTO(savedCountry);
    }

    public void deleteByIsoCode(String isoCode) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));
        countryRepository.delete(country);
    }

    public CountryDTO update(String isoCode, CountryDTO countryDTO) {
        CountryEntity country = countryRepository.findById(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));
        country.setName(countryDTO.getName());
        CountryEntity updatedCountry = countryRepository.save(country);
        return countryMapper.toCountryDTO(updatedCountry);
    }

}
