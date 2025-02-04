package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.services.CountryService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    public CountryController(CountryService countryService, CountryMapper countryMapper) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
    }

    @PostMapping("/")
    public ResponseEntity<CountryDto> saveCountry(@RequestBody CountryDto countryDto) {
        CountryDto savedCountry = countryService.save(countryDto);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<CountryDto> getCountryByIsoCode(@PathVariable String isoCode) {
        CountryEntity countryEntity = countryService.findByIsoCode(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ISO code: " + isoCode));

        CountryDto countryDto = countryMapper.toCountryDto(countryEntity);

        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @PatchMapping("/{isoCode}")
    public ResponseEntity<CountryDto> updateCountryByIsoCode(@PathVariable String isoCode, @RequestBody CountryDto countryDto) {
        CountryDto updatedCountry = countryService.update(isoCode, countryDto);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> deleteCountryByIsoCode(@PathVariable String isoCode) {
        countryService.deleteByIsoCode(isoCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}