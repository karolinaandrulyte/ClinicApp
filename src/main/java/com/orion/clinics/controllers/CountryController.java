package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.services.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<CountryEntity> countryEntityOptional = countryService.findByIsoCode(isoCode);

        if (countryEntityOptional.isPresent()) {
            CountryDto countryDto = countryMapper.toCountryDto(countryEntityOptional.get());
            return new ResponseEntity<>(countryDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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