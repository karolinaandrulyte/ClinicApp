package com.orion.clinics.controllers;

import com.orion.clinics.dtos.ClinicStatusDto;
import com.orion.clinics.services.ClinicStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinic-status")
public class ClinicStatusController {

    private final ClinicStatusService clinicStatusService;

    public ClinicStatusController(ClinicStatusService clinicStatusService) {
        this.clinicStatusService = clinicStatusService;
    }

    @PostMapping("/")
    public ResponseEntity<ClinicStatusDto> saveClinicStatus(@RequestBody ClinicStatusDto clinicStatusDto) {
        ClinicStatusDto savedClinicStatus = clinicStatusService.save(clinicStatusDto);
        return new ResponseEntity<>(savedClinicStatus, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClinicStatusDto>> getAllClinicStatus() {
        return ResponseEntity.ok(clinicStatusService.findAll());
    }

    @GetMapping("/exists/{status}")
    public ResponseEntity<Boolean> checkIfClinicStatusExists(@PathVariable String status) {
        boolean exists = clinicStatusService.existsByStatus(status);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/{status}")
    public ResponseEntity<Void> deleteClinicStatusByStatus(@PathVariable String status) {
        clinicStatusService.deleteByStatus(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
