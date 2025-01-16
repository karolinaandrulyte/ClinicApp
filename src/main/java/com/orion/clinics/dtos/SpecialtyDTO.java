package com.orion.clinics.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class SpecialtyDTO {
    private String name;
    private Set<DoctorDTO> doctors;
}
