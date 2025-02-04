package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicDto;
import com.orion.clinics.entities.ClinicEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    ClinicDto toClinicDto(ClinicEntity clinicEntity);
    ClinicEntity toClinicEntity(ClinicDto clinicDto);
}
