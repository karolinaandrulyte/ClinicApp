package com.orion.clinics.controllers;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.services.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping("/specialty/")
    public ResponseEntity<SpecialtyDto> saveSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        SpecialtyDto savedSpecialty = specialtyService.save(specialtyDto);
        return new ResponseEntity<>(savedSpecialty, HttpStatus.CREATED);
    }

}
