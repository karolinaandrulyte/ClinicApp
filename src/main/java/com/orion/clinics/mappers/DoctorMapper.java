package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorDTO;
import com.orion.clinics.entities.DoctorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDoctorDTO(DoctorEntity doctorEntity);
    DoctorEntity toDoctorEntity(DoctorDTO doctorDTO);
}
