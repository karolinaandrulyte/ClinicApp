package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicStatusDTO;
import com.orion.clinics.entities.ClinicStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicStatusMapper {
    ClinicStatusDTO toClinicStatusDTO(ClinicStatusEntity clinicStatusEntity);
    ClinicStatusEntity toClinicStatusEntity(ClinicStatusDTO clinicStatusDTO);
}

