package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicRecordDTO;
import com.orion.clinics.entities.ClinicRecordEntity;
import com.orion.clinics.mappers.ClinicRecordMapper;
import com.orion.clinics.repositories.ClinicRecordRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class ClinicRecordService {

    private final ClinicRecordRepository clinicRecordRepository;
    private final ClinicRecordMapper clinicRecordMapper;

    public ClinicRecordService(ClinicRecordRepository clinicRecordRepository, ClinicRecordMapper clinicRecordMapper) {
        this.clinicRecordRepository = clinicRecordRepository;
        this.clinicRecordMapper = clinicRecordMapper;
    }

    public List<ClinicRecordDTO> findAll() {
        List<ClinicRecordEntity> clinicRecords = clinicRecordRepository.findAll();
        return clinicRecords.stream()
                .map(clinicRecordMapper::toClinicRecordDTO)
                .toList();
    }

    public Optional<ClinicRecordDTO> findById(Long id) {
        if (!clinicRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + id);
        }
        Optional<ClinicRecordEntity> clinicRecord = clinicRecordRepository.findById(id);
        return clinicRecord.map(clinicRecordMapper::toClinicRecordDTO);
    }

    public ClinicRecordDTO save(ClinicRecordDTO clinicRecordDTO) {
        ClinicRecordEntity clinicRecord = clinicRecordMapper.toClinicRecordEntity(clinicRecordDTO);
        ClinicRecordEntity savedClinicRecord = clinicRecordRepository.save(clinicRecord);
        return clinicRecordMapper.toClinicRecordDTO(savedClinicRecord);
    }

    public void deleteById(Long id) {
        if (!clinicRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + id);
        }
        clinicRecordRepository.deleteById(id);
    }

    public ClinicRecordDTO update(ClinicRecordDTO clinicRecordDTO) {
        ClinicRecordEntity clinicRecord = clinicRecordMapper.toClinicRecordEntity(clinicRecordDTO);
        ClinicRecordEntity updatedClinicRecord = clinicRecordRepository.save(clinicRecord);
        return clinicRecordMapper.toClinicRecordDTO(updatedClinicRecord);
    }
}
