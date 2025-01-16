package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicRecordDTO;
import com.orion.clinics.entities.ClinicRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicRecordMapper {
    ClinicRecordDTO toClinicRecordDTO(ClinicRecordEntity clinicRecordEntity);
    ClinicRecordEntity toClinicRecordEntity(ClinicRecordDTO clinicRecordDTO);
}
