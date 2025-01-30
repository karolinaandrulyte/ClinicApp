package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorDTO;
import com.orion.clinics.entities.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDoctorDTO(DoctorEntity doctorEntity);
    DoctorEntity toDoctorEntity(DoctorDTO doctorDTO);
}
