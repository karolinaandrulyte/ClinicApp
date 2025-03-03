package com.orion.clinics.services;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.entities.SpecialtyEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.SpecialtyMapper;
import com.orion.clinics.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyService(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    public SpecialtyDto save(SpecialtyDto specialtyDto) {
        if(specialtyRepository.existsById(specialtyDto.getName())) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "Specialty with name " + specialtyDto.getName() + " already exists.");
        }
        SpecialtyEntity specialty = specialtyMapper.toSpecialtyEntity(specialtyDto);
        SpecialtyEntity savedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toSpecialtyDto(savedSpecialty);
    }

    public List<SpecialtyDto> findAll() {
        List<SpecialtyEntity> specialties = specialtyRepository.findAll();
        return specialties.stream()
                .map(specialtyMapper::toSpecialtyDto)
                .toList();
    }

    public boolean existsByName(String name) {
        return specialtyRepository.existsById(name);
    }

    public void deleteByName(String name) {
        SpecialtyEntity specialty = specialtyRepository.findById(name)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Specialty not found with name: " + name));
        specialtyRepository.delete(specialty);
    }
}