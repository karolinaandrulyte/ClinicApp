package com.orion.clinics.mappers;

import com.orion.clinics.dtos.CityDto;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.entities.CountryEntity;
import com.orion.clinics.services.CountryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = CountryService.class)
public interface CityMapper {
    @Mapping(source = "country.isoCode", target = "countryIsoCode")
    CityDto toCityDto(CityEntity cityEntity);

    @Mapping(source = "countryIsoCode", target = "country", qualifiedByName = "mapCountry")
    CityEntity toCityEntity(CityDto cityDto);

    @Named("mapCountry")
    default CountryEntity mapCountry(String countryIsoCode) {
        if (countryIsoCode == null) {
            return null;
        }
        CountryEntity country = new CountryEntity();
        country.setIsoCode(countryIsoCode);
        return country;
    }

}