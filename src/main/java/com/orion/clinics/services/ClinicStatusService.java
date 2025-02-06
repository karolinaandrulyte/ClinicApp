package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicStatusDto;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.mappers.ClinicStatusMapper;
import com.orion.clinics.repositories.ClinicStatusRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicStatusService {

    private final ClinicStatusRepository clinicStatusRepository;
    private final ClinicStatusMapper clinicStatusMapper;

    public ClinicStatusService(ClinicStatusRepository clinicStatusRepository, ClinicStatusMapper clinicStatusMapper) {
        this.clinicStatusRepository = clinicStatusRepository;
        this.clinicStatusMapper = clinicStatusMapper;
    }

    public ClinicStatusDto save(ClinicStatusDto clinicStatusDto) {
        ClinicStatusEntity clinicStatus = clinicStatusMapper.toClinicStatusEntity(clinicStatusDto);
        ClinicStatusEntity savedClinicStatus = clinicStatusRepository.save(clinicStatus);
        return clinicStatusMapper.toClinicStatusDto(savedClinicStatus);
    }

    public List<ClinicStatusDto> findAll() {
        List<ClinicStatusEntity> clinicStatuses = clinicStatusRepository.findAll();
        return clinicStatuses.stream()
                .map(clinicStatusMapper::toClinicStatusDto)
                .toList();
    }

    public boolean existsByStatus(String status) {
        return clinicStatusRepository.existsByStatus(status);
    }

    public void deleteByStatus(String status) {
        ClinicStatusEntity clinicStatus = clinicStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic status not found with status: " + status));
        clinicStatusRepository.delete(clinicStatus);
    }

}