package com.orion.clinics.services;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.RecordStatusMapper;
import com.orion.clinics.repositories.RecordStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Record status not found with status: " + status));
        recordStatusRepository.delete(recordStatus);
    }

}