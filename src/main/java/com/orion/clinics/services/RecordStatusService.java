package com.orion.clinics.services;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.mappers.RecordStatusMapper;
import com.orion.clinics.repositories.RecordStatusRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordStatusService {
    private final RecordStatusRepository recordStatusRepository;
    private final RecordStatusMapper recordStatusMapper;

    public RecordStatusService(RecordStatusRepository recordStatusRepository, RecordStatusMapper recordStatusMapper) {
        this.recordStatusRepository = recordStatusRepository;
        this.recordStatusMapper = recordStatusMapper;
    }

    public List<RecordStatusDto> findAll() {
        List<RecordStatusEntity> recordStatuses = recordStatusRepository.findAll();
        return recordStatuses.stream()
                .map(recordStatusMapper::toRecordStatusDto)
                .toList();
    }

    public Optional<RecordStatusDto> findByStatus(String status) {
        RecordStatusEntity recordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        return Optional.of(recordStatusMapper.toRecordStatusDto(recordStatus));
    }

    public RecordStatusDto save(RecordStatusDto recordStatusDto) {
        RecordStatusEntity recordStatus = recordStatusMapper.toRecordStatusEntity(recordStatusDto);
        RecordStatusEntity savedRecordStatus = recordStatusRepository.save(recordStatus);
        return recordStatusMapper.toRecordStatusDto(savedRecordStatus);
    }

    public void deleteByStatus(String status) {
        RecordStatusEntity recordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        recordStatusRepository.delete(recordStatus);
    }

    public RecordStatusDto update(String status, RecordStatusDto recordStatusDto) {
        RecordStatusEntity existingRecordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        existingRecordStatus.setStatus(recordStatusDto.getStatus());
        RecordStatusEntity updatedRecordStatus = recordStatusRepository.save(existingRecordStatus);
        return recordStatusMapper.toRecordStatusDto(updatedRecordStatus);
    }
}