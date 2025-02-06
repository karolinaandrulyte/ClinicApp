package com.orion.clinics.mappers;

import com.orion.clinics.dtos.SpecialtyDto;
import com.orion.clinics.entities.SpecialtyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    SpecialtyDto toSpecialtyDto(SpecialtyEntity specialtyEntity);

    SpecialtyEntity toSpecialtyEntity(SpecialtyDto specialtyDto);

}

