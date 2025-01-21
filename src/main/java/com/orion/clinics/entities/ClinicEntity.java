package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "clinics")
public class ClinicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @ManyToOne
    @JoinColumn(name = "status_name")  // Links to clinic_status table
    private ClinicStatusEntity status;

    @ManyToMany
    @JoinTable(
            name = "clinics_documents",
            joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<DocumentEntity> documents;

    @OneToMany(mappedBy = "clinic")
    private List<ClinicRecordEntity> records;

    @ManyToMany
    @JoinTable(
            name = "doctors_clinics",
            joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<DoctorEntity> doctors;
}


