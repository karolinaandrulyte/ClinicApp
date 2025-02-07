package com.orion.clinics.controllers;

import com.orion.clinics.dtos.ClinicDto;
import com.orion.clinics.dtos.ClinicRecordDto;
import com.orion.clinics.services.CityService;
import com.orion.clinics.services.ClinicRecordService;
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
    private final ClinicRecordService clinicRecordService;

    public ClinicController(ClinicService clinicService, ClinicRecordService clinicRecordService) {
        this.clinicService = clinicService;
        this.clinicRecordService = clinicRecordService;
    }

    @PostMapping("/")
    public ResponseEntity<ClinicDto> saveClinic(@RequestBody ClinicDto clinicDto) {
        ClinicDto savedClinic = clinicService.saveClinic(clinicDto);
        return new ResponseEntity<>(savedClinic, HttpStatus.CREATED);
    }

    @PostMapping("/records/")
    public ResponseEntity<ClinicRecordDto> saveClinicRecord(@RequestBody ClinicRecordDto clinicRecordDto) {
        ClinicRecordDto savedClinicRecord = clinicRecordService.save(clinicRecordDto);
        return new ResponseEntity<>(savedClinicRecord, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClinicDto>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicDto> getClinicById(@PathVariable Long id) {
        Optional<ClinicDto> clinic = clinicService.findById(id);
        return clinic
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/records")
    public ResponseEntity<List<ClinicRecordDto>> getClinicRecordsByClinicId(@PathVariable Long id) {
        List<ClinicRecordDto> clinicRecords = clinicRecordService.findByClinicId(id);
        return ResponseEntity.ok(clinicRecords);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClinicDto> updateClinicById(@PathVariable Long id, @RequestBody ClinicDto clinicDto) {
        ClinicDto updatedClinic = clinicService.updateClinic(id, clinicDto);
        return new ResponseEntity<>(updatedClinic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicById(@PathVariable Long id) {
        clinicService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}