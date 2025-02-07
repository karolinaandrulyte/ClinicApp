package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CityDto;
import com.orion.clinics.entities.CityEntity;
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

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @PostMapping("/")
    public ResponseEntity<CityDto> saveCity(@RequestBody CityDto cityDto) {
        CityDto savedCity = cityService.save(cityDto);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return ResponseEntity.ok(cityService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        Optional<CityEntity> cityEntityOptional = cityService.findById(id);

        if (cityEntityOptional.isPresent()) {
            CityDto cityDto = cityMapper.toCityDto(cityEntityOptional.get());
            return new ResponseEntity<>(cityDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CityDto> updateCityById(@PathVariable Long id, @RequestBody CityDto cityDto) {
        CityDto updatedCity = cityService.update(id, cityDto);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable Long id) {
        cityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}