package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorDTO;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.mappers.DoctorMapper;
import com.orion.clinics.repositories.DoctorRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<DoctorDTO> findAll() {
        List<DoctorEntity> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDoctorDTO)
                .toList();
    }

    public Optional<DoctorDTO> findById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        Optional<DoctorEntity> doctor = doctorRepository.findById(id);
        return doctor.map(doctorMapper::toDoctorDTO);
    }

    public DoctorDTO save(DoctorDTO doctorDTO) {
        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorDTO);
        DoctorEntity savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDoctorDTO(savedDoctor);
    }

    public void deleteById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
