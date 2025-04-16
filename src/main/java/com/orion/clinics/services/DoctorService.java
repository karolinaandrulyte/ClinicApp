package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorCreateDto;
import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.dtos.DoctorUpdateDto;
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
    public DoctorDto save(DoctorCreateDto doctorCreateDto) {
        if (doctorCreateDto.getSpecialtyNames() == null || doctorCreateDto.getSpecialtyNames().isEmpty()) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "At least one specialty is required.");
        }

        if (doctorCreateDto.getClinicIds() == null || doctorCreateDto.getClinicIds().isEmpty()) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "At least one clinic is required.");
        }

        DoctorEntity doctor = doctorMapper.toDoctorEntity(doctorCreateDto);

        Set<SpecialtyEntity> existingSpecialties = doctorCreateDto.getSpecialtyNames().stream()
                .map(specialtyName  -> specialtyRepository.findByName(specialtyName)
                        .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Specialty not found: " + specialtyName, specialtyName )))
                .collect(Collectors.toSet());
        doctor.setSpecialties(existingSpecialties);

        List<ClinicEntity> clinics = doctorCreateDto.getClinicIds().stream()
                .map(clinicId -> clinicRepository.findById(clinicId)
                        .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Clinic not found with id: " + clinicId, clinicId.toString())))
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
        List<DoctorEntity> doctors = doctorRepository.findByDoctorType_Type(doctorType);
        return doctors.stream()
                .map(doctorMapper::toDoctorDto)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public DoctorDto update(Long id, DoctorUpdateDto doctorUpdateDto) {
        DoctorEntity existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor with ID " + id + " not found"));

        doctorMapper.updateDoctorEntityFromDto(doctorUpdateDto, existingDoctor);

        DoctorEntity updatedDoctor = doctorRepository.save(existingDoctor); // since transactional, no need to save
        return doctorMapper.toDoctorDto(updatedDoctor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor with ID " + id + " not found");
        }
        doctorRepository.deleteById(id);
    }
}