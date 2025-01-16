package com.orion.clinics.mappers;

import com.orion.clinics.dtos.CityDTO;
import com.orion.clinics.entities.CityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toCityDTO(CityEntity cityEntity);
    CityEntity toCityEntity(CityDTO cityDTO);
}
