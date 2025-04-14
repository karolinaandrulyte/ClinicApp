package com.orion.clinics.controllers;

import com.orion.clinics.dtos.DoctorCreateDto;
import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.dtos.DoctorUpdateDto;
import com.orion.clinics.services.DoctorRecordService;
import com.orion.clinics.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRecordService doctorRecordService;

    public DoctorController(DoctorService doctorService, DoctorRecordService doctorRecordService) {
        this.doctorService = doctorService;
        this.doctorRecordService = doctorRecordService;
    }

    @PostMapping("/")
    public ResponseEntity<DoctorDto> saveDoctor(@RequestBody DoctorCreateDto doctorCreateDto) {
        DoctorDto savedDoctor = doctorService.save(doctorCreateDto);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }
    
    @PostMapping("/records/")
    public ResponseEntity<DoctorRecordDto> saveDoctorRecord(@RequestBody DoctorRecordDto doctorRecordDto) {
        DoctorRecordDto savedDoctorRecord = doctorRecordService.save(doctorRecordDto);
        return new ResponseEntity<>(savedDoctorRecord, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        DoctorDto doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/{doctorId}/records")
    public ResponseEntity<List<DoctorRecordDto>> getDoctorRecords(@PathVariable Long doctorId) {
        List<DoctorRecordDto> doctorRecords = doctorRecordService.getAllRecordsByDoctorId(doctorId);
        return new ResponseEntity<>(doctorRecords, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctorById(@PathVariable Long id, @RequestBody DoctorUpdateDto doctorUpdateDto) {
        DoctorDto updatedDoctor = doctorService.update(id, doctorUpdateDto);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorById(@PathVariable Long id) {
        doctorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}