package com.orion.clinics.services;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.enums.ClinicsAppErrors;
import com.orion.clinics.enums.RecordStatus;
import com.orion.clinics.exception.ApiException;
import com.orion.clinics.mappers.RecordStatusMapper;
import com.orion.clinics.repositories.RecordStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordStatusService {

    private final RecordStatusRepository recordStatusRepository;
    private final RecordStatusMapper recordStatusMapper;

    public RecordStatusService(RecordStatusRepository recordStatusRepository, RecordStatusMapper recordStatusMapper) {
        this.recordStatusRepository = recordStatusRepository;
        this.recordStatusMapper = recordStatusMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public RecordStatusDto save(RecordStatusDto recordStatusDto) {
        if (recordStatusRepository.existsByStatus(recordStatusDto.getStatus())) {
            throw new ApiException(ClinicsAppErrors.INVALID_ARGUMENT, "Record status already exists: " + recordStatusDto.getStatus());
        }
        RecordStatusEntity recordStatus = recordStatusMapper.toRecordStatusEntity(recordStatusDto);
        RecordStatusEntity savedRecordStatus = recordStatusRepository.save(recordStatus);
        return recordStatusMapper.toRecordStatusDto(savedRecordStatus);
    }

    @Transactional
    public List<RecordStatusDto> findAll() {
        List<RecordStatusEntity> recordStatuses = recordStatusRepository.findAll();
        return recordStatuses.stream()
                .map(recordStatusMapper::toRecordStatusDto)
                .toList();
    }

    @Transactional
    public boolean existsByStatus(String status) {
        return recordStatusRepository.existsByStatus(status);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByStatus(String status) {
        RecordStatus recordStatus = RecordStatus.valueOf(status);
        RecordStatusEntity recordStatusEntity = recordStatusRepository.findByStatus(recordStatus)
                .orElseThrow(() -> new ApiException(ClinicsAppErrors.ENTITY_NOT_FOUND, "Record status not found with status: " + status));
        recordStatusRepository.delete(recordStatusEntity);
    }

}