package com.orion.clinics.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ClinicDTO {
    private Long id;
    private String name;
    private String address;
    private CityDTO city;
    private List<DocumentDTO> documents;
    private List<ClinicRecordDTO> records;
    private List<DoctorDTO> doctors;
}
