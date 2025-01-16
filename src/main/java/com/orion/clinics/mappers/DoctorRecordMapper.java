package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorRecordDTO;
import com.orion.clinics.entities.DoctorRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorRecordMapper {
    DoctorRecordDTO toDoctorRecordDTO(DoctorRecordEntity doctorRecordEntity);
    DoctorRecordEntity toDoctorRecordEntity(DoctorRecordDTO doctorRecordDTO);
}
