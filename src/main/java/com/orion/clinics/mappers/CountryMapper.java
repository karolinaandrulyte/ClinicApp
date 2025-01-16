package com.orion.clinics.mappers;

import com.orion.clinics.dtos.CountryDTO;
import com.orion.clinics.entities.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // MapStruct processor will produce a singleton Spring Bean mapper injectable wherever needed
public interface CountryMapper {
    CountryDTO toCountryDTO(CountryEntity countryEntity);
    CountryEntity toCountryEntity(CountryDTO countryDTO);
}
