package com.orion.clinics.services;

import com.orion.clinics.dtos.ClinicDto;
import com.orion.clinics.entities.*;
import com.orion.clinics.mappers.ClinicMapper;
import com.orion.clinics.repositories.ClinicRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public ClinicService(ClinicRepository clinicRepository, ClinicMapper clinicMapper) {
        this.clinicRepository = clinicRepository;
        this.clinicMapper = clinicMapper;
    }

    public List<ClinicDto> findAll() {
        List<ClinicEntity> clinics = clinicRepository.findAll();
        return clinics.stream()
                .map(clinicMapper::toClinicDto)
                .toList();
    }

    public Optional<ClinicDto> findById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic record not found with id: " + id);
        }
        Optional<ClinicEntity> clinic = clinicRepository.findById(id);
        return clinic.map(clinicMapper::toClinicDto);
    }

    public ClinicDto saveClinic(ClinicDto clinicDto) {
        ClinicEntity clinic = clinicMapper.toClinicEntity(clinicDto);
        ClinicEntity savedClinic = clinicRepository.save(clinic);
        return clinicMapper.toClinicDto(savedClinic);
    }

    public ClinicDto updateClinic(Long id, ClinicDto clinicDto) {
        if (clinicDto.getId() == null || !clinicRepository.existsById(clinicDto.getId())) {
            throw new ResourceNotFoundException("Clinic not found with id: " + clinicDto.getId());
        }
        ClinicEntity existingClinic = clinicRepository.findById(clinicDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicDto.getId()));
        if (clinicDto.getName() != null) {
            existingClinic.setName(clinicMapper.toClinicEntity(clinicDto).getName());
        }
        if (clinicDto.getAddress() != null) {
            existingClinic.setAddress(clinicMapper.toClinicEntity(clinicDto).getAddress());
        }
        if (clinicDto.getCity() != null) {
            existingClinic.setCity(clinicMapper.toClinicEntity(clinicDto).getCity());
        }
        ClinicEntity updatedClinic = clinicRepository.save(existingClinic);
        return clinicMapper.toClinicDto(updatedClinic);
    }

    public void deleteById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clinic not found with id: " + id);
        }
        clinicRepository.deleteById(id);
    }
}