package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorRecordDTO;
import com.orion.clinics.entities.DoctorRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorRecordMapper {
    @Mapping(target = "doctorId", source = "doctor.id")
    DoctorRecordDTO toDoctorRecordDTO(DoctorRecordEntity doctorRecordEntity);

    @Mapping(target = "doctor.id", source = "doctorId")
    DoctorRecordEntity toDoctorRecordEntity(DoctorRecordDTO doctorRecordDTO);
}
