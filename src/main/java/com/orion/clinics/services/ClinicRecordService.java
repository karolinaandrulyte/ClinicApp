package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicRecordDto;
import com.orion.clinics.entities.ClinicEntity;
import com.orion.clinics.entities.ClinicRecordEntity;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.ClinicRecordMapper;
import com.orion.clinics.repositories.ClinicRecordRepository;
import com.orion.clinics.repositories.ClinicRepository;
import com.orion.clinics.repositories.ClinicStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClinicRecordService {

    private final ClinicRecordRepository clinicRecordRepository;
    private final ClinicRecordMapper clinicRecordMapper;
    private final ClinicStatusRepository clinicStatusRepository;
    private final ClinicRepository clinicRepository;

    public ClinicRecordService(ClinicRecordRepository clinicRecordRepository,
                               ClinicRecordMapper clinicRecordMapper,
                               ClinicStatusRepository clinicStatusRepository,
                               ClinicRepository clinicRepository) {
        this.clinicRecordRepository = clinicRecordRepository;
        this.clinicRecordMapper = clinicRecordMapper;
        this.clinicStatusRepository = clinicStatusRepository;
        this.clinicRepository = clinicRepository;
    }

    public ClinicRecordDto save(ClinicRecordDto clinicRecordDto) {
        ClinicRecordEntity clinicRecord = clinicRecordMapper.toClinicRecordEntity(clinicRecordDto);
        ClinicRecordEntity savedClinicRecord = clinicRecordRepository.save(clinicRecord);
        return clinicRecordMapper.toClinicRecordDto(savedClinicRecord);
    }

    public ClinicRecordDto saveUsingBuilder(ClinicRecordDto clinicRecordDto) {
        ClinicStatusEntity status = clinicStatusRepository.findById(clinicRecordDto.getStatusName()).orElse(null);
        ClinicEntity clinic = clinicRepository.findById(clinicRecordDto.getClinicId()).orElse(null);

        ClinicRecordEntity clinicRecord = ClinicRecordEntity.builder()
                .setStatus(status)
                .setUpdated(clinicRecordDto.getUpdated())
                .setClinic(clinic)
                .build();

        ClinicRecordEntity savedClinicRecord = clinicRecordRepository.save(clinicRecord);
        return clinicRecordMapper.toClinicRecordDto(savedClinicRecord);
    }

    public List<ClinicRecordDto> findAll() {
        List<ClinicRecordEntity> clinicRecords = clinicRecordRepository.findAll();
        return clinicRecords.stream()
                .map(clinicRecordMapper::toClinicRecordDto)
                .toList();
    }

    public ClinicRecordDto findById(Long id) {
        ClinicRecordEntity clinicRecord = clinicRecordRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic record not found with id: " + id));
        return clinicRecordMapper.toClinicRecordDto(clinicRecord);
    }

    public List<ClinicRecordDto> findByClinicId(Long clinicId) {
        List<ClinicRecordEntity> clinicRecords = clinicRecordRepository.findByClinicId(clinicId);
        return clinicRecords.stream()
                .map(clinicRecordMapper::toClinicRecordDto)
                .toList();
    }

    public ClinicRecordDto update(ClinicRecordDto clinicRecordDto) {
        if (clinicRecordDto.getId() == null || !clinicRecordRepository.existsById(clinicRecordDto.getId())) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic record not found with id: " + clinicRecordDto.getId());
        }
        ClinicRecordEntity existingClinicRecord = clinicRecordRepository.findById(clinicRecordDto.getId())
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic record not found with id: " + clinicRecordDto.getId()));
        if (clinicRecordDto.getStatusName() != null) {
            existingClinicRecord.setStatus(clinicRecordMapper.toClinicRecordEntity(clinicRecordDto).getStatus());
        }
        if (clinicRecordDto.getUpdated() != null) {
            existingClinicRecord.setUpdated(clinicRecordDto.getUpdated());
        }
        if (clinicRecordDto.getClinicId() != null) {
            existingClinicRecord.setClinic(clinicRecordMapper.toClinicRecordEntity(clinicRecordDto).getClinic());
        }
        ClinicRecordEntity updatedClinicRecord = clinicRecordRepository.save(existingClinicRecord);
        return clinicRecordMapper.toClinicRecordDto(updatedClinicRecord);
    }

    public void deleteById(Long id) {
        if (!clinicRecordRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic record not found with id: " + id);
        }
        clinicRecordRepository.deleteById(id);
    }
}