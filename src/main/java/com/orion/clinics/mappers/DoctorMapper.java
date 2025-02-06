package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.entities.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class, ClinicMapper.class})
public interface DoctorMapper {
    @Mapping(target = "specialties", source = "specialties")
    @Mapping(target = "clinics", source = "clinics")
    DoctorDto toDoctorDto(DoctorEntity doctorEntity);

    @Mapping(target = "specialties", source = "specialties")
    @Mapping(target = "clinics", source = "clinics")
    DoctorEntity toDoctorEntity(DoctorDto doctorDto);

    List<DoctorDto> toDoctorDtos(List<DoctorEntity> doctors);
    List<DoctorEntity> toDoctorEntities(List<DoctorDto> doctors);
}