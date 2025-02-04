package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicRecordDto;
import com.orion.clinics.entities.ClinicRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicRecordMapper {
    ClinicRecordDto toClinicRecordDto(ClinicRecordEntity clinicRecordEntity);
    ClinicRecordEntity toClinicRecordEntity(ClinicRecordDto clinicRecordDto);
}
