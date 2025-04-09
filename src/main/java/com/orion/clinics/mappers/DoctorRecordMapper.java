package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorRecordDto;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.enums.RecordStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DoctorRecordMapper {
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "clinicId", source = "clinic.id")
    @Mapping(target = "status", source = "status.status")
    DoctorRecordDto toDoctorRecordDto(DoctorRecordEntity doctorRecordEntity);

    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "clinic", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatusToEntity")
    @Mapping(target = "updated", ignore = true)
    DoctorRecordEntity toDoctorRecordEntity(DoctorRecordDto doctorRecordDto);

    @Named("mapStatusToEntity")
    default RecordStatusEntity mapStatusToEntity(String status) {
        if (status == null) {
            return null;
        }
        return new RecordStatusEntity(RecordStatus.valueOf(status), RecordStatus.valueOf(status).getDescription());
    }
}