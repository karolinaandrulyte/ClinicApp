package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private String countryIsoCode;
}