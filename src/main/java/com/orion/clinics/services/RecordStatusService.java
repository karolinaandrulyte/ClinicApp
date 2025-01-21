package com.orion.clinics.services;

import com.orion.clinics.dtos.RecordStatusDTO;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.mappers.RecordStatusMapper;
import com.orion.clinics.repositories.RecordStatusRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class RecordStatusService {
    private final RecordStatusRepository recordStatusRepository;
    private final RecordStatusMapper recordStatusMapper;

    public RecordStatusService(RecordStatusRepository recordStatusRepository, RecordStatusMapper recordStatusMapper) {
        this.recordStatusRepository = recordStatusRepository;
        this.recordStatusMapper = recordStatusMapper;
    }

    public List<RecordStatusDTO> findAll() {
        List<RecordStatusEntity> recordStatuses = recordStatusRepository.findAll();
        return recordStatuses.stream()
                .map(recordStatusMapper::toRecordStatusDTO)
                .toList();
    }

    public Optional<RecordStatusDTO> findByStatus(String status) {
        RecordStatusEntity recordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        return Optional.of(recordStatusMapper.toRecordStatusDTO(recordStatus));
    }

    public RecordStatusDTO save(RecordStatusDTO recordStatusDTO) {
        RecordStatusEntity recordStatus = recordStatusMapper.toRecordStatusEntity(recordStatusDTO);
        RecordStatusEntity savedRecordStatus = recordStatusRepository.save(recordStatus);
        return recordStatusMapper.toRecordStatusDTO(savedRecordStatus);
    }

    public void deleteByStatus(String status) {
        RecordStatusEntity recordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        recordStatusRepository.delete(recordStatus);
    }

    public RecordStatusDTO update(String status, RecordStatusDTO recordStatusDTO) {
        RecordStatusEntity existingRecordStatus = recordStatusRepository.findById(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));

        // Update fields
        existingRecordStatus.setStatus(recordStatusDTO.getStatus());

        RecordStatusEntity updatedRecordStatus = recordStatusRepository.save(existingRecordStatus);
        return recordStatusMapper.toRecordStatusDTO(updatedRecordStatus);
    }
}
