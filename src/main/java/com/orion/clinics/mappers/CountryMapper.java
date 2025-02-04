package com.orion.clinics.mappers;

import com.orion.clinics.dtos.CountryDto;
import com.orion.clinics.entities.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // MapStruct processor will produce a singleton Spring Bean mapper injectable wherever needed
public interface CountryMapper {
    CountryDto toCountryDto(CountryEntity countryEntity);
    CountryEntity toCountryEntity(CountryDto countryDto);
}
