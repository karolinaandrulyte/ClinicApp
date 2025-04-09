package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DoctorDto;
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
    @Mapping(target = "doctorType", source = "doctorType")
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

}