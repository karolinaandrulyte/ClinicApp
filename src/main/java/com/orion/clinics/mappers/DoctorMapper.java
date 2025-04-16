package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorCreateDto;
import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.dtos.DoctorUpdateDto;
import com.orion.clinics.entities.*;
import com.orion.clinics.enums.DoctorType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class, ClinicMapper.class})
public interface DoctorMapper {

    @Mapping(target = "specialties", source = "specialties")
    @Mapping(target = "clinics", source = "clinics")
    DoctorDto toDoctorDto(DoctorEntity doctorEntity);

    default DoctorEntity toDoctorEntity(DoctorCreateDto doctorCreateDto) {
        if (doctorCreateDto.getDoctorType() == null) {
            throw new IllegalArgumentException("Doctor type must not be null");
        }

        DoctorEntity doctorEntity = getDoctorEntityByDoctorType(doctorCreateDto.getDoctorType());
        doctorEntity.setFirstName(doctorCreateDto.getFirstName());
        doctorEntity.setLastName(doctorCreateDto.getLastName());
        doctorEntity.setDateOfBirth(doctorCreateDto.getDateOfBirth());
        doctorEntity.setAddress(doctorCreateDto.getAddress());
        doctorEntity.setPhoneNumber(doctorCreateDto.getPhoneNumber());
        doctorEntity.setEmail(doctorCreateDto.getEmail());
        doctorEntity.setDoctorType(mapEnumToEntity(doctorCreateDto.getDoctorType()));
        return doctorEntity;
    }

    default void updateDoctorEntityFromDto(DoctorUpdateDto dto, DoctorEntity entity) {
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getDateOfBirth() != null) entity.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getDoctorType() != null) entity.setDoctorType(mapEnumToEntity(dto.getDoctorType()));
    }

    default DoctorEntity getDoctorEntityByDoctorType(DoctorType doctorType) {
        if (doctorType == null) {
            throw new IllegalArgumentException("Doctor type must not be null");
        }

        return switch (doctorType) {
            case CHIEF -> new ChiefOfDepartment();
            case RESIDENT -> new Resident();
            case INTERN -> new Intern();
        };
    }

    private DoctorTypeEntity mapEnumToEntity(DoctorType doctorType) {
        if (doctorType == null) return null;
        DoctorTypeEntity entity = new DoctorTypeEntity();
        entity.setType(doctorType);
        return entity;
    }

    default DoctorType mapEntityToEnum(DoctorTypeEntity entity) {
        return entity != null ? entity.getType() : null;
    }

}