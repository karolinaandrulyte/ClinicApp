package com.orion.clinics.controllers;

import com.orion.clinics.dtos.*;
import com.orion.clinics.services.ClinicService;
import com.orion.clinics.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ClinicService clinicService;
    private final DoctorService doctorService;

    public AdminController(ClinicService clinicService, DoctorService doctorService) {
        this.clinicService = clinicService;
        this.doctorService = doctorService;
    }

    @PostMapping("/clinic/")
    public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {
        ClinicDTO savedClinic = clinicService.saveClinic(clinicDTO);
        return new ResponseEntity<>(savedClinic, HttpStatus.CREATED);
    }

    @PostMapping("/doctor/")
    public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO savedDoctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

//    @PostMapping("/admin/")
//    public void registerAdmin() {
//    Register a new admin? need entity?
//    }

    @GetMapping("/clinicDummy")
    public ClinicDTO getDummyClinic() {
        CountryDTO dummyCountry = new CountryDTO("DUM", "DummyCountry");
        CityDTO dummyCity = new CityDTO(4L, "New Work", dummyCountry, null);
        return new ClinicDTO(1L, "Dummy Clinic", "1234 Dummy St", dummyCity, null);
    }

    @GetMapping("/doctorDummy")
    public DoctorDTO getDummyDoctor() {
        Date dateOfBirth = Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        Set<SpecialtyDTO> specialties = Set.of(new SpecialtyDTO("therapist"));
        return new DoctorDTO(1L, "First", "Last", dateOfBirth, "1234 Doctor St", "1234567890", "doctor@example.com", specialties, List.of());
    }

    @GetMapping("/clinics")
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/clinics/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        Optional<ClinicDTO> clinic = clinicService.findById(id);
        return clinic
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        Optional<DoctorDTO> doctor = doctorService.findById(id);
        return doctor
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/clinics/{id}")
    public ResponseEntity<ClinicDTO> updateClinicById(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        ClinicDTO updatedClinic = clinicService.updateClinic(id, clinicDTO);
        return new ResponseEntity<>(updatedClinic, HttpStatus.OK);
    }

    @PatchMapping("/doctors/{id}")
    public ResponseEntity<DoctorDTO> updateDoctorById(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.update(id, doctorDTO);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }
}
