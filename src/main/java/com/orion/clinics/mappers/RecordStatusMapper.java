package com.orion.clinics.mappers;

import com.orion.clinics.dtos.RecordStatusDTO;
import com.orion.clinics.entities.RecordStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordStatusMapper {
    RecordStatusDTO toDoctorStatusDTO(RecordStatusEntity doctorStatusEntity);
    RecordStatusEntity toDoctorStatusEntity(RecordStatusDTO doctorStatusDTO);
}

