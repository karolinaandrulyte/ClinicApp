package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.entities.RecordStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorRecordMapper {
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(source = "status.status", target = "status")
    DoctorRecordDto toDoctorRecordDto(DoctorRecordEntity doctorRecordEntity);

    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(source = "status", target = "status.status")
    DoctorRecordEntity toDoctorRecordEntity(DoctorRecordDto doctorRecordDto);

    String map(RecordStatusEntity status);

}
