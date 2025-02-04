package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicStatusDto;
import com.orion.clinics.entities.ClinicStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicStatusMapper {
    ClinicStatusDto toClinicStatusDto(ClinicStatusEntity clinicStatusEntity);
    ClinicStatusEntity toClinicStatusEntity(ClinicStatusDto clinicStatusDto);
}

