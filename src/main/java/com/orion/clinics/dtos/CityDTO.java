package com.orion.clinics.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CityDTO {
    private Long id;
    private String name;
    private CountryDTO country;
    private List<ClinicDTO> clinics;
}
