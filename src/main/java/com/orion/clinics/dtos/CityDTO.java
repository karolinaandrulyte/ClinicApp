package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    private CountryDTO country;
    private List<ClinicDTO> clinics;
}