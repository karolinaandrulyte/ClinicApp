package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicDTO;
import com.orion.clinics.entities.ClinicEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    ClinicDTO toClinicDTO(ClinicEntity clinicEntity);
    ClinicEntity toClinicEntity(ClinicDTO clinicDTO);
}
