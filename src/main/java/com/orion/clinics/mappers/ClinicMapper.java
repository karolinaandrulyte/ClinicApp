package com.orion.clinics.mappers;

import com.orion.clinics.dtos.ClinicDto;
import com.orion.clinics.entities.ClinicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    @Mapping(source = "city.id", target = "cityId")
    ClinicDto toClinicDto(ClinicEntity clinicEntity);

    @Mapping(source = "cityId", target = "city.id")
    ClinicEntity toClinicEntity(ClinicDto clinicDto);

    List<ClinicDto> toClinicDtos(List<ClinicEntity> clinics);
    List<ClinicEntity> toClinicEntities(List<ClinicDto> clinics);
}
