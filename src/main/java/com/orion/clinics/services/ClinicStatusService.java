package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicStatusDTO;
import com.orion.clinics.entities.ClinicStatusEntity;
import com.orion.clinics.mappers.ClinicStatusMapper;
import com.orion.clinics.repositories.ClinicStatusRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class ClinicStatusService {

    private final ClinicStatusRepository clinicStatusRepository;
    private final ClinicStatusMapper clinicStatusMapper;

    public ClinicStatusService(ClinicStatusRepository clinicStatusRepository, ClinicStatusMapper clinicStatusMapper) {
        this.clinicStatusRepository = clinicStatusRepository;
        this.clinicStatusMapper = clinicStatusMapper;
    }

    public List<ClinicStatusDTO> findAll() {
        List<ClinicStatusEntity> clinicStatuses = clinicStatusRepository.findAll();
        return clinicStatuses.stream()
                .map(clinicStatusMapper::toClinicStatusDTO)
                .toList();
    }

    public Optional<ClinicStatusDTO> findByStatus(String status) {
        ClinicStatusEntity clinicStatus = clinicStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic status not found with status: " + status));
        return Optional.of(clinicStatusMapper.toClinicStatusDTO(clinicStatus));
    }

    public ClinicStatusDTO save(ClinicStatusDTO clinicStatusDTO) {
        ClinicStatusEntity clinicStatus = clinicStatusMapper.toClinicStatusEntity(clinicStatusDTO);
        ClinicStatusEntity savedClinicStatus = clinicStatusRepository.save(clinicStatus);
        return clinicStatusMapper.toClinicStatusDTO(savedClinicStatus);
    }

    public void deleteByStatus(String status) {
        ClinicStatusEntity clinicStatus = clinicStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic status not found with status: " + status));
        clinicStatusRepository.delete(clinicStatus);
    }

    public ClinicStatusDTO update(String status, ClinicStatusDTO clinicStatusDTO) {
        ClinicStatusEntity existingClinicStatus = clinicStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic status not found with status: " + status));
        existingClinicStatus.setStatus(clinicStatusDTO.getStatus());

        ClinicStatusEntity updatedClinicStatus = clinicStatusRepository.save(existingClinicStatus);
        return clinicStatusMapper.toClinicStatusDTO(updatedClinicStatus);
    }
}
