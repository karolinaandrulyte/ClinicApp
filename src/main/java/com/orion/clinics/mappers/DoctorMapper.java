package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorCreateDto;
import com.orion.clinics.dtos.DoctorDto;
import com.orion.clinics.dtos.DoctorUpdateDto;
import com.orion.clinics.entities.ChiefOfDepartment;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.Intern;
import com.orion.clinics.entities.Resident;
import com.orion.clinics.enums.NameUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class, ClinicMapper.class})
public interface DoctorMapper {
    @Mapping(target = "specialties", source = "specialties")
    @Mapping(target = "clinics", source = "clinics")
    DoctorDto toDoctorDto(DoctorEntity doctorEntity);

    default DoctorEntity toDoctorEntity(DoctorDto doctorDto) {
        if (doctorDto == null) {
            return null;
        }

        DoctorEntity doctorEntity = switch (doctorDto.getDoctorType()) {
            case NameUtils.CHIEF -> new ChiefOfDepartment();
            case NameUtils.RESIDENT -> new Resident();
            case NameUtils.INTERN -> new Intern();
            default -> throw new IllegalArgumentException("Invalid doctor type: " + doctorDto.getDoctorType());
        };

        doctorEntity.setFirstName(doctorDto.getFirstName());
        doctorEntity.setLastName(doctorDto.getLastName());
        doctorEntity.setDateOfBirth(doctorDto.getDateOfBirth());
        doctorEntity.setAddress(doctorDto.getAddress());
        doctorEntity.setPhoneNumber(doctorDto.getPhoneNumber());
        doctorEntity.setEmail(doctorDto.getEmail());
        doctorEntity.setDoctorType(doctorDto.getDoctorType());

        return doctorEntity;
    }

    default DoctorEntity toDoctorEntity(DoctorCreateDto doctorCreateDto) {
        DoctorEntity doctorEntity = getDoctorEntityByDoctorType(doctorCreateDto.getDoctorType());
        doctorEntity.setFirstName(doctorCreateDto.getFirstName());
        doctorEntity.setLastName(doctorCreateDto.getLastName());
        doctorEntity.setDateOfBirth(doctorCreateDto.getDateOfBirth());
        doctorEntity.setAddress(doctorCreateDto.getAddress());
        doctorEntity.setPhoneNumber(doctorCreateDto.getPhoneNumber());
        doctorEntity.setEmail(doctorCreateDto.getEmail());
        doctorEntity.setDoctorType(doctorCreateDto.getDoctorType());
        return doctorEntity;
    }

    default void updateDoctorEntityFromDto(DoctorUpdateDto dto, DoctorEntity entity) {
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getDateOfBirth() != null) entity.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getDoctorType() != null) entity.setDoctorType(dto.getDoctorType());
    }

    default DoctorEntity getDoctorEntityByDoctorType(String doctorType) {
        return switch (doctorType) {
            case NameUtils.CHIEF -> new ChiefOfDepartment();
            case NameUtils.RESIDENT -> new Resident();
            case NameUtils.INTERN -> new Intern();
            default -> throw new IllegalArgumentException("Invalid doctor type: " + doctorType);
        };
    }

}