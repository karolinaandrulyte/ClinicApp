package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicStatusDto;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.ClinicStatusMapper;
import com.orion.clinics.repositories.ClinicStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClinicStatusService {

    private final ClinicStatusRepository clinicStatusRepository;
    private final ClinicStatusMapper clinicStatusMapper;

    public ClinicStatusService(ClinicStatusRepository clinicStatusRepository, ClinicStatusMapper clinicStatusMapper) {
        this.clinicStatusRepository = clinicStatusRepository;
        this.clinicStatusMapper = clinicStatusMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public ClinicStatusDto save(ClinicStatusDto clinicStatusDto) {
        if (clinicStatusRepository.existsByStatus(clinicStatusDto.getStatus())) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "Clinic status already exists: " + clinicStatusDto.getStatus());
        }
        ClinicStatusEntity clinicStatus = clinicStatusMapper.toClinicStatusEntity(clinicStatusDto);
        ClinicStatusEntity savedClinicStatus = clinicStatusRepository.save(clinicStatus);
        return clinicStatusMapper.toClinicStatusDto(savedClinicStatus);
    }

    @Transactional
    public List<ClinicStatusDto> findAll() {
        List<ClinicStatusEntity> clinicStatuses = clinicStatusRepository.findAll();
        return clinicStatuses.stream()
                .map(clinicStatusMapper::toClinicStatusDto)
                .toList();
    }

    @Transactional
    public boolean existsByStatus(String status) {
        return clinicStatusRepository.existsByStatus(status);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByStatus(String status) {
        ClinicStatusEntity clinicStatus = clinicStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic status not found with status: " + status));
        clinicStatusRepository.delete(clinicStatus);
    }

}