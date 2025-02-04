package com.orion.clinics.mappers;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.entities.SpecialtyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    SpecialtyDto toSpecialtyDto(SpecialtyEntity specialtyEntity);

    @Mapping(target = "doctors", ignore = true)
    SpecialtyEntity toSpecialtyEntity(SpecialtyDto specialtyDto);

    List<SpecialtyDto> toSpecialtyDtos(List<SpecialtyEntity> specialties);
    List<SpecialtyEntity> toSpecialtyEntities(List<SpecialtyDto> specialties);
}

