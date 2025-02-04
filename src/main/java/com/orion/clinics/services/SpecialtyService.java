package com.orion.clinics.services;

import com.orion.clinics.dtos.SpecialtyDto;
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

    public List<SpecialtyDto> findAll() {
        List<SpecialtyEntity> specialties = specialtyRepository.findAll();
        return specialties.stream()
                .map(specialtyMapper::toSpecialtyDto)
                .toList();
    }

    public Optional<SpecialtyDto> findByName(String name) {
        SpecialtyEntity specialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        return Optional.of(specialtyMapper.toSpecialtyDto(specialty));
    }

    public SpecialtyDto save(SpecialtyDto specialtyDto) {
        SpecialtyEntity specialty = specialtyMapper.toSpecialtyEntity(specialtyDto);
        SpecialtyEntity savedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toSpecialtyDto(savedSpecialty);
    }

    public void deleteByName(String name) {
        SpecialtyEntity specialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        specialtyRepository.delete(specialty);
    }

    public SpecialtyDto update(String name, SpecialtyDto specialtyDto) {
        SpecialtyEntity existingSpecialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with name: " + name));
        existingSpecialty.setName(specialtyDto.getName());
        SpecialtyEntity updatedSpecialty = specialtyRepository.save(existingSpecialty);
        return specialtyMapper.toSpecialtyDto(updatedSpecialty);
    }
}