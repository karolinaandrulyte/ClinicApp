package com.orion.clinics.dtos;

import lombok.Data;

@Data
public class CityDTO {
    private Long id;
    private String name;
    private String country_ISO_code;
}
