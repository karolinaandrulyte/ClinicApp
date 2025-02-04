package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorRecordMapper {
    @Mapping(target = "doctorId", source = "doctor.id")
    DoctorRecordDto toDoctorRecordDto(DoctorRecordEntity doctorRecordEntity);

    @Mapping(target = "doctor.id", source = "doctorId")
    DoctorRecordEntity toDoctorRecordEntity(DoctorRecordDto doctorRecordDto);
}
