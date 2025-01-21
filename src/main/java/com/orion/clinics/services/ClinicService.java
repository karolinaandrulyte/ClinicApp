package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicDTO;
import com.orion.clinics.entities.*;
import com.orion.clinics.mappers.ClinicMapper;
import com.orion.clinics.repositories.ClinicRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public ClinicService(ClinicRepository clinicRepository, ClinicMapper clinicMapper) {
        this.clinicRepository = clinicRepository;
        this.clinicMapper = clinicMapper;
    }

    public List<ClinicDTO> findAll() {
        List<ClinicEntity> clinics = clinicRepository.findAll();
        return clinics.stream()
                .map(clinicMapper::toClinicDTO)
                .toList();
    }

    public Optional<ClinicDTO> findById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + id);
        }
        Optional<ClinicEntity> clinic = clinicRepository.findById(id);
        return clinic.map(clinicMapper::toClinicDTO);
    }

    public ClinicDTO saveClinic(ClinicDTO clinicDto) {
        ClinicEntity clinic = clinicMapper.toClinicEntity(clinicDto);
        ClinicEntity savedClinic = clinicRepository.save(clinic);
        return clinicMapper.toClinicDTO(savedClinic);
    }

    public void deleteById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic not found with id: " + id);
        }
        clinicRepository.deleteById(id);
    }
}
