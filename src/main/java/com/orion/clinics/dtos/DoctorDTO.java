package com.orion.clinics.dtos;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private Set<SpecialtyDTO> specialties;
    private List<ClinicDTO> clinics;
    private List<DoctorRecordDTO> records;
}
