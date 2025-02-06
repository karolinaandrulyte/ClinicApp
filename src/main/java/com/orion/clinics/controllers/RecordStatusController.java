package com.orion.clinics.controllers;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.services.RecordStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record-status")
public class RecordStatusController {

    private final RecordStatusService recordStatusService;

    public RecordStatusController(RecordStatusService recordStatusService) {
        this.recordStatusService = recordStatusService;
    }

    @PostMapping("/")
    public ResponseEntity<RecordStatusDto> saveRecordStatus(@RequestBody RecordStatusDto recordStatusDto) {
        RecordStatusDto savedRecordStatus = recordStatusService.save(recordStatusDto);
        return new ResponseEntity<>(savedRecordStatus, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecordStatusDto>> getAllRecordStatus() {
        return ResponseEntity.ok(recordStatusService.findAll());
    }

    @GetMapping("/exists/{status}")
    public ResponseEntity<Boolean> checkIfRecordStatusExists(@PathVariable String status) {
        boolean exists = recordStatusService.existsByStatus(status);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @DeleteMapping("/{status}")
    public ResponseEntity<Void> deleteRecordStatusByStatus(@PathVariable String status) {
        recordStatusService.deleteByStatus(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
