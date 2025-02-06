package com.orion.clinics.controllers;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.services.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping("/")
    public ResponseEntity<SpecialtyDto> saveSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        SpecialtyDto savedSpecialty = specialtyService.save(specialtyDto);
        return new ResponseEntity<>(savedSpecialty, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDto>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> checkIfSpecialtyExists(@PathVariable String name) {
        boolean exists = specialtyService.existsByName(name);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteSpecialtyByName(@PathVariable String name) {
        specialtyService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}