package com.orion.clinics.mappers;

import com.orion.clinics.dtos.SpecialtyDTO;
import com.orion.clinics.entities.SpecialtyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {
    SpecialtyDTO toSpecialtyDTO(SpecialtyEntity specialtyEntity);
    SpecialtyEntity toSpecialtyEntity(SpecialtyDTO specialtyDTO);
}

