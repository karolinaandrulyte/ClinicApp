package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorRecordDTO;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.mappers.DoctorRecordMapper;
import com.orion.clinics.repositories.DoctorRecordRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class DoctorRecordService {
    private final DoctorRecordRepository doctorRecordRepository;
    private final DoctorRecordMapper doctorRecordMapper;

    public DoctorRecordService(DoctorRecordRepository doctorRecordRepository, DoctorRecordMapper doctorRecordMapper) {
        this.doctorRecordRepository = doctorRecordRepository;
        this.doctorRecordMapper = doctorRecordMapper;
    }

    public List<DoctorRecordDTO> findAll() {
        List<DoctorRecordEntity> doctorRecords = doctorRecordRepository.findAll();
        return doctorRecords.stream()
                .map(doctorRecordMapper::toDoctorRecordDTO)
                .toList();
    }

    public Optional<DoctorRecordDTO> findById(Long id) {
        if (!doctorRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor record not found with id: " + id);
        }
        Optional<DoctorRecordEntity> doctorRecord = doctorRecordRepository.findById(id);
        return doctorRecord.map(doctorRecordMapper::toDoctorRecordDTO);
    }

    public DoctorRecordDTO save(DoctorRecordDTO doctorRecordDTO) {
        DoctorRecordEntity doctorRecord = doctorRecordMapper.toDoctorRecordEntity(doctorRecordDTO);
        DoctorRecordEntity savedDoctorRecord = doctorRecordRepository.save(doctorRecord);
        return doctorRecordMapper.toDoctorRecordDTO(savedDoctorRecord);
    }

    public void deleteById(Long id) {
        if (!doctorRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor record not found with id: " + id);
        }
        doctorRecordRepository.deleteById(id);
    }
}
