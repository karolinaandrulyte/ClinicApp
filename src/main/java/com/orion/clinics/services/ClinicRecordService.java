package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicRecordDTO;
import com.orion.clinics.entities.ClinicRecordEntity;
import com.orion.clinics.mappers.ClinicRecordMapper;
import com.orion.clinics.repositories.ClinicRecordRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public ClinicRecordDTO update(ClinicRecordDTO clinicRecordDTO) {
        if (clinicRecordDTO.getId() == null || !clinicRecordRepository.existsById(clinicRecordDTO.getId())) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + clinicRecordDTO.getId());
        }
        ClinicRecordEntity existingClinicRecord = clinicRecordRepository.findById(clinicRecordDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic record not found with id: " + clinicRecordDTO.getId()));
        if (clinicRecordDTO.getStatusName() != null) {
            existingClinicRecord.setStatusName(clinicRecordMapper.toClinicRecordEntity(clinicRecordDTO).getStatusName());
        }
        if (clinicRecordDTO.getUpdated() != null) {
            existingClinicRecord.setUpdated(clinicRecordDTO.getUpdated());
        }
        if (clinicRecordDTO.getClinic() != null) {
            existingClinicRecord.setClinic(clinicRecordMapper.toClinicRecordEntity(clinicRecordDTO).getClinic());
        }
        ClinicRecordEntity updatedClinicRecord = clinicRecordRepository.save(existingClinicRecord);
        return clinicRecordMapper.toClinicRecordDTO(updatedClinicRecord);
    }

    public void deleteById(Long id) {
        if (!clinicRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + id);
        }
        clinicRecordRepository.deleteById(id);
    }
}
