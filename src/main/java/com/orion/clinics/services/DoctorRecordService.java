package com.orion.clinics.services;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.mappers.DoctorRecordMapper;
import com.orion.clinics.repositories.DoctorRecordRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorRecordService {
    private final DoctorRecordRepository doctorRecordRepository;
    private final DoctorRecordMapper doctorRecordMapper;

    public DoctorRecordService(DoctorRecordRepository doctorRecordRepository, DoctorRecordMapper doctorRecordMapper) {
        this.doctorRecordRepository = doctorRecordRepository;
        this.doctorRecordMapper = doctorRecordMapper;
    }

    public List<DoctorRecordDto> findAll() {
        List<DoctorRecordEntity> doctorRecords = doctorRecordRepository.findAll();
        return doctorRecords.stream()
                .map(doctorRecordMapper::toDoctorRecordDto)
                .toList();
    }

    public Optional<DoctorRecordDto> findById(Long id) {
        if (!doctorRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor record not found with id: " + id);
        }
        Optional<DoctorRecordEntity> doctorRecord = doctorRecordRepository.findById(id);
        return doctorRecord.map(doctorRecordMapper::toDoctorRecordDto);
    }

    public DoctorRecordDto save(DoctorRecordDto doctorRecordDto) {
        DoctorRecordEntity doctorRecord = doctorRecordMapper.toDoctorRecordEntity(doctorRecordDto);
        DoctorRecordEntity savedDoctorRecord = doctorRecordRepository.save(doctorRecord);
        return doctorRecordMapper.toDoctorRecordDto(savedDoctorRecord);
    }

    public DoctorRecordDto update(DoctorRecordDto doctorRecordDto) {
        Long id = doctorRecordDto.getId();
        if (!doctorRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor record not found with id: " + id);
        }
        DoctorRecordEntity doctorRecord = doctorRecordMapper.toDoctorRecordEntity(doctorRecordDto);
        DoctorRecordEntity updatedDoctorRecord = doctorRecordRepository.save(doctorRecord);
        return doctorRecordMapper.toDoctorRecordDto(updatedDoctorRecord);
    }

    public void deleteById(Long id) {
        if (!doctorRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor record not found with id: " + id);
        }
        doctorRecordRepository.deleteById(id);
    }
}