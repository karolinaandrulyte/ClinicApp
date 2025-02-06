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

    public RecordStatusDto save(RecordStatusDto recordStatusDto) {
        RecordStatusEntity recordStatus = recordStatusMapper.toRecordStatusEntity(recordStatusDto);
        RecordStatusEntity savedRecordStatus = recordStatusRepository.save(recordStatus);
        return recordStatusMapper.toRecordStatusDto(savedRecordStatus);
    }

    public List<RecordStatusDto> findAll() {
        List<RecordStatusEntity> recordStatuses = recordStatusRepository.findAll();
        return recordStatuses.stream()
                .map(recordStatusMapper::toRecordStatusDto)
                .toList();
    }

    public boolean existsByStatus(String status) {
        return recordStatusRepository.existsByStatus(status);
    }

    public void deleteByStatus(String status) {
        RecordStatusEntity recordStatus = recordStatusRepository.findByStatus(status)
                .orElseThrow(() -> new ResourceNotFoundException("Record status not found with status: " + status));
        recordStatusRepository.delete(recordStatus);
    }

}