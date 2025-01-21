package com.orion.clinics.mappers;

import com.orion.clinics.dtos.RecordStatusDTO;
import com.orion.clinics.entities.RecordStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordStatusMapper {
    RecordStatusDTO toRecordStatusDTO(RecordStatusEntity recordStatusEntity);
    RecordStatusEntity toRecordStatusEntity(RecordStatusDTO recordStatusDTO);
}

