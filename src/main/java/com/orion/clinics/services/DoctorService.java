package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.entities.ClinicEntity;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.SpecialtyEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DoctorMapper;
import com.orion.clinics.repositories.ClinicRepository;
import com.orion.clinics.repositories.DoctorRepository;
import com.orion.clinics.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorMapper doctorMapper;
    private final ClinicRepository clinicRepository;

    public DoctorService(DoctorRepository doctorRepository, SpecialtyRepository specialtyRepository, DoctorMapper doctorMapper, ClinicRepository clinicRepository) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
        this.doctorMapper = doctorMapper;
        this.clinicRepository = clinicRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public DoctorDto save(DoctorDto doctorDto) {
        if (doctorDto.getSpecialties() == null || doctorDto.getSpecialties().isEmpty()) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "At least one specialty is required.");
        }

        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorDto);

        Set<SpecialtyEntity> existingSpecialties = doctorDto.getSpecialties().stream()
                .map(specialtyDto -> specialtyRepository.findByName(specialtyDto.getName())
                        .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Specialty not found: " + specialtyDto.getName(), specialtyDto.getName())))
                .collect(Collectors.toSet());
        doctor.getSpecialties().clear();
        doctor.setSpecialties(existingSpecialties);

        List<ClinicEntity> clinics = doctorDto.getClinics().stream()
                .map(clinicDto -> clinicRepository.findById(clinicDto.getId())
                        .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic not found with id: " + clinicDto.getId(), clinicDto.getId().toString())))
                .collect(Collectors.toList());
        doctor.setClinics(clinics);

        DoctorEntity savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDoctorDto(savedDoctor);
    }

    @Transactional
    public List<DoctorDto> findAll() {
        List<? extends DoctorEntity> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDoctorDto)
                .toList();
    }

    @Transactional
    public DoctorDto findById(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor not found with id: " + id));
        return doctorMapper.toDoctorDto(doctor);
    }

    @Transactional
    public List<DoctorDto> findByDoctorType(String doctorType) {
        List<DoctorEntity> doctors = doctorRepository.findByDoctorType(doctorType);
        return doctors.stream()
                .map(doctorMapper::toDoctorDto)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public DoctorDto update(Long id, DoctorDto doctorDto) {
        DoctorEntity existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor with ID " + id + " not found"));

        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorDto);
        doctor.setId(existingDoctor.getId());

        if (doctorDto.getDoctorType() != null) {
            doctor.setDoctorType(doctorDto.getDoctorType());
        }

//        DoctorEntity updatedDoctor = doctorRepository.save(doctor); since transactional, no need to save
        return doctorMapper.toDoctorDto(existingDoctor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor with ID " + id + " not found");
        }
        doctorRepository.deleteById(id);
    }
}