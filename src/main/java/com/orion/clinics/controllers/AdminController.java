package com.orion.clinics.controllers;

import com.orion.clinics.dtos.*;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ClinicService clinicService;
    private final DoctorService doctorService;
    private final CountryService countryService;
    private final CityService cityService;
    private final SpecialtyService specialtyService;
    private final CityMapper cityMapper;

    public AdminController(ClinicService clinicService, DoctorService doctorService, CountryService countryService, CityService cityService, SpecialtyService specialtyService, CountryMapper countryMapper, CityMapper cityMapper) {
        this.clinicService = clinicService;
        this.doctorService = doctorService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.specialtyService = specialtyService;
        this.cityMapper = cityMapper;
    }

    @PostMapping("/clinic/")
    public ResponseEntity<ClinicDto> saveClinic(@RequestBody ClinicDto clinicDto) {
        ClinicDto savedClinic = clinicService.saveClinic(clinicDto);
        return new ResponseEntity<>(savedClinic, HttpStatus.CREATED);
    }

    @PostMapping("/doctor/")
    public ResponseEntity<DoctorDto> saveDoctor(@RequestBody DoctorDto doctorDto) {
        DoctorDto savedDoctor = doctorService.save(doctorDto);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

//    @PostMapping("/admin/")
//    public void registerAdmin() {
//    Register a new admin? need entity?
//    }

    @GetMapping("/clinics")
    public ResponseEntity<List<ClinicDto>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/clinics/{id}")
    public ResponseEntity<ClinicDto> getClinicById(@PathVariable Long id) {
        Optional<ClinicDto> clinic = clinicService.findById(id);
        return clinic
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        Optional<DoctorDto> doctor = doctorService.findById(id);
        return doctor
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/clinics/{id}")
    public ResponseEntity<ClinicDto> updateClinicById(@PathVariable Long id, @RequestBody ClinicDto clinicDto) {
        ClinicDto updatedClinic = clinicService.updateClinic(id, clinicDto);
        return new ResponseEntity<>(updatedClinic, HttpStatus.OK);
    }

    @PatchMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> updateDoctorById(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        DoctorDto updatedDoctor = doctorService.update(id, doctorDto);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }
}
