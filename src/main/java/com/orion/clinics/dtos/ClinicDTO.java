package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDTO {
    private Long id;
    private String name;
    private String address;
    private CityDTO city;
    private List<ClinicRecordDTO> records;
}
