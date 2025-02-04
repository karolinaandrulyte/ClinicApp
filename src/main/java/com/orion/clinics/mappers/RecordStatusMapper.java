package com.orion.clinics.mappers;

import com.orion.clinics.dtos.RecordStatusDto;
import com.orion.clinics.entities.RecordStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordStatusMapper {
    RecordStatusDto toRecordStatusDto(RecordStatusEntity recordStatusEntity);
    RecordStatusEntity toRecordStatusEntity(RecordStatusDto recordStatusDto);
}

