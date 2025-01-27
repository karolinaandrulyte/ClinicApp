package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryDTO {
    private String isoCode;
    private String name;
}
