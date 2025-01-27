package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CountryDTO;
import com.orion.clinics.services.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/dummy")
    public CountryDTO getDummyCountry() {
        return new CountryDTO ("DUM", "DummyCountry");
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> getCountryByIsoCode(@PathVariable String isoCode) {
        Optional<CountryDTO> country = countryService.findByIsoCode(isoCode);
        return country
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody CountryDTO countryDTO) {
        CountryDTO savedCountry = countryService.save(countryDTO);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @PutMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> updateCountryByIsoCode(@PathVariable String isoCode, @RequestBody CountryDTO countryDTO) {
        CountryDTO updatedCountry = countryService.update(isoCode, countryDTO);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> deleteCountryByIsoCode(@PathVariable String isoCode) {
        countryService.deleteByIsoCode(isoCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}