package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.DoctorRecordMapper;
import com.orion.clinics.repositories.DoctorRecordRepository;
import com.orion.clinics.repositories.DoctorRepository;
import com.orion.clinics.repositories.RecordStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorRecordService {
    private final DoctorRecordRepository doctorRecordRepository;
    private final DoctorRecordMapper doctorRecordMapper;
    private final RecordStatusRepository recordStatusRepository;
    private final DoctorRepository doctorRepository;

    public DoctorRecordService(DoctorRecordRepository doctorRecordRepository, DoctorRecordMapper doctorRecordMapper, RecordStatusRepository recordStatusRepository, DoctorRepository doctorRepository) {
        this.doctorRecordRepository = doctorRecordRepository;
        this.doctorRecordMapper = doctorRecordMapper;
        this.recordStatusRepository = recordStatusRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public DoctorRecordDto save(DoctorRecordDto doctorRecordDto) {
        RecordStatusEntity status = recordStatusRepository.findByStatus(doctorRecordDto.getStatus())
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Status not found: " + doctorRecordDto.getStatus()));
        DoctorEntity doctor = doctorRepository.findById(doctorRecordDto.getDoctorId())
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor not found: " + doctorRecordDto.getDoctorId()));

        DoctorRecordEntity doctorRecord = new DoctorRecordEntity();
        doctorRecord.setUpdated(doctorRecordDto.getUpdated());
        doctorRecord.setStatus(status);
        doctorRecord.setDoctor(doctor);

        DoctorRecordEntity savedDoctorRecord = doctorRecordRepository.save(doctorRecord);

        return new DoctorRecordDto(savedDoctorRecord.getId(), savedDoctorRecord.getUpdated(),
                        savedDoctorRecord.getStatus().getStatus(), savedDoctorRecord.getDoctor().getId());
    }

    @Transactional
    public List<DoctorRecordDto> findAll() {
        List<DoctorRecordEntity> doctorRecords = doctorRecordRepository.findAll();
        return doctorRecords.stream()
                .map(doctorRecordMapper::toDoctorRecordDto)
                .toList();
    }

    @Transactional
    public DoctorRecordDto findById(Long id) {
        DoctorRecordEntity doctorRecord = doctorRecordRepository.findById(id)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor record not found with id: " + id));
        return doctorRecordMapper.toDoctorRecordDto(doctorRecord);
    }

    @Transactional
    public List<DoctorRecordDto> getAllRecordsByDoctorId(Long doctorId) {
        List<DoctorRecordEntity> doctorRecordEntities = doctorRecordRepository.findByDoctorId(doctorId);

        return doctorRecordEntities.stream()
                .map(doctorRecordMapper::toDoctorRecordDto)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public DoctorRecordDto update(DoctorRecordDto doctorRecordDto) {
        Long id = doctorRecordDto.getId();
        if (!doctorRecordRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor record not found with id: " + id);
        }
        DoctorRecordEntity doctorRecord = doctorRecordMapper.toDoctorRecordEntity(doctorRecordDto);
//        DoctorRecordEntity updatedDoctorRecord = doctorRecordRepository.save(doctorRecord); since transactional, no need to save
        return doctorRecordMapper.toDoctorRecordDto(doctorRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (!doctorRecordRepository.existsById(id)) {
            throw new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Doctor record not found with id: " + id);
        }
        doctorRecordRepository.deleteById(id);
    }
}