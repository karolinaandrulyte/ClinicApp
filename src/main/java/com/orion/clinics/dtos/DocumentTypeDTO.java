package com.orion.clinics.dtos;

import com.orion.clinics.entities.ClinicRecordEntity;
import com.orion.clinics.entities.DoctorEntity;
import com.orion.clinics.entities.DocumentEntity;
import lombok.Data;

import java.util.List;

@Data
public class DocumentTypeDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private List<DocumentEntity> documents;
    private List<ClinicRecordEntity> records;
    private List<DoctorEntity> doctors;
}
