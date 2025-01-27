package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CityDTO;
import com.orion.clinics.dtos.CountryDTO;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.services.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityMapper cityMapper;
    private final CountryController countryController;
    private final CityService cityService;

    public CityController(CityMapper cityMapper, CountryController countryController, CityService cityService) {
        this.cityMapper = cityMapper;
        this.countryController = countryController;
        this.cityService = cityService;
    }

    @GetMapping("/dummy")
    public CityDTO getDummyCity() {
        CountryEntity dummyCountry = new CountryEntity("DUM", "DummyCountry");
        CityEntity dummyCityEntity = new CityEntity(1L, "DummyCity", dummyCountry, null);
        return cityMapper.toCityDTO(dummyCityEntity);
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(Long id) {
        Optional<CityDTO> city = cityService.findById(id);
        return city
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> saveCity(@RequestBody CityDTO cityDTO) {
        CityDTO savedCity = cityService.save(cityDTO);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCityById(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        CityDTO updatedCity = cityService.update(id, cityDTO);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable Long id) {
        cityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}