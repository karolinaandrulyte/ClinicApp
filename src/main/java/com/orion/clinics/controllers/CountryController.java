package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.services.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
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
        CountryDto countryDto = countryService.findByIsoCode(isoCode);
        return ResponseEntity.ok(countryDto);
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