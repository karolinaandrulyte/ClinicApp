package com.orion.clinics.dtos;

import com.orion.clinics.entities.ClinicEntity;
import com.orion.clinics.entities.DoctorRecordEntity;
import com.orion.clinics.entities.SpecialtyEntity;
import lombok.Data;

import java.util.List;

@Data
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<SpecialtyEntity> specialties;
    private List<DoctorRecordEntity> doctorRecords;
    private List<ClinicEntity> clinics;
}
