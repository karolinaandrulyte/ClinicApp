package com.orion.clinics.controllers;

import com.orion.clinics.dtos.CityDTO;
import com.orion.clinics.dtos.ClinicDTO;
import com.orion.clinics.dtos.CountryDTO;
import com.orion.clinics.services.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/dummy")
    public ClinicDTO getDummyClinic() {
        CountryDTO dummyCountry = new CountryDTO("DUM", "DummyCountry");
        CityDTO dummyCity = new CityDTO(4L, "New Work", dummyCountry, null);
        return new ClinicDTO(1L, "Dummy Clinic", "1234 Dummy St", dummyCity, null);
    }

    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        Optional<ClinicDTO> clinic = clinicService.findById(id);
        return clinic
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {
        ClinicDTO savedClinic = clinicService.saveClinic(clinicDTO);
        return new ResponseEntity<>(savedClinic, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ClinicDTO> updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
//        ClinicDTO updatedClinic = clinicService.updateClinic(id, clinicDTO);
//        return new ResponseEntity<>(updatedClinic, HttpStatus.OK);
//    }

//    @PostMapping()
//    public ResponseEntity<Appointment>

}
