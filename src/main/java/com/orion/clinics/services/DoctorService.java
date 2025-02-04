package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.SpecialtyEntity;
import com.orion.clinics.mappers.DoctorMapper;
import com.orion.clinics.repositories.DoctorRepository;
import com.orion.clinics.repositories.SpecialtyRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(DoctorRepository doctorRepository, SpecialtyRepository specialtyRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<DoctorDto> findAll() {
        List<DoctorEntity> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDoctorDto)
                .toList();
    }

    public Optional<DoctorDto> findById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        Optional<DoctorEntity> doctor = doctorRepository.findById(id);
        return doctor.map(doctorMapper::toDoctorDto);
    }

    public DoctorDto save(DoctorDto doctorDto) {
        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorDto);
        Set<SpecialtyEntity> existingSpecialties = new HashSet<>();
        for (SpecialtyDto specialtyDto : doctorDto.getSpecialties()) {
            SpecialtyEntity specialty = specialtyRepository.findByName(specialtyDto.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Specialty not found: " + specialtyDto.getName()));
            existingSpecialties.add(specialty);
        }
        doctor.setSpecialties(existingSpecialties);

        DoctorEntity savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDoctorDto(savedDoctor);
    }

    public DoctorDto update(Long id, DoctorDto doctorDto) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorDto);
        doctor.setId(id);
        DoctorEntity updatedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDoctorDto(updatedDoctor);
    }

    public void deleteById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}