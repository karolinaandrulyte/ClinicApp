package com.orion.clinics.services;

import com.orion.clinics.dtos.SpecialtyDTO;
import com.orion.clinics.entities.SpecialtyEntity;
import com.orion.clinics.mappers.SpecialtyMapper;
import com.orion.clinics.repositories.SpecialtyRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyService(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    public List<SpecialtyDTO> findAll() {
        List<SpecialtyEntity> specialties = specialtyRepository.findAll();
        return specialties.stream()
                .map(specialtyMapper::toSpecialtyDTO)
                .toList();
    }

    public Optional<SpecialtyDTO> findByName(String name) {
        SpecialtyEntity specialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        return Optional.of(specialtyMapper.toSpecialtyDTO(specialty));
    }

    public SpecialtyDTO save(SpecialtyDTO specialtyDTO) {
        SpecialtyEntity specialty = specialtyMapper.toSpecialtyEntity(specialtyDTO);
        SpecialtyEntity savedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toSpecialtyDTO(savedSpecialty);
    }

    public void deleteByName(String name) {
        SpecialtyEntity specialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        specialtyRepository.delete(specialty);
    }

    public SpecialtyDTO update(String name, SpecialtyDTO specialtyDTO) {
        SpecialtyEntity existingSpecialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        existingSpecialty.setName(specialtyDTO.getName());
        SpecialtyEntity updatedSpecialty = specialtyRepository.save(existingSpecialty);
        return specialtyMapper.toSpecialtyDTO(updatedSpecialty);
    }
}
