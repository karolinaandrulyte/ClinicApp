package com.orion.clinics.controllers;

import com.orion.clinics.dtos.DoctorDTO;
import com.orion.clinics.dtos.SpecialtyDTO;
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
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/dummy")
    public DoctorDTO getDummyDoctor() {
        Date dateOfBirth = Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        Set<SpecialtyDTO> specialties = Set.of(new SpecialtyDTO("therapist"));
        return new DoctorDTO(1L, "First", "Last", dateOfBirth, "1234 Doctor St", "1234567890", "doctor@example.com", specialties, List.of());
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        Optional<DoctorDTO> doctor = doctorService.findById(id);
        return doctor
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO savedDoctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctorById(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.update(id, doctorDTO);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

}
