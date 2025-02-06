package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicRecordDto;
import com.orion.clinics.entities.ClinicRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicRecordMapper {

    @Mapping(source="clinic.id", target="clinicId")
    @Mapping(source = "status.status", target = "statusName")
    ClinicRecordDto toClinicRecordDto(ClinicRecordEntity clinicRecordEntity);

    @Mapping(source = "clinicId", target = "clinic.id")
    @Mapping(source = "statusName", target = "status.status")
    ClinicRecordEntity toClinicRecordEntity(ClinicRecordDto clinicRecordDto);
}