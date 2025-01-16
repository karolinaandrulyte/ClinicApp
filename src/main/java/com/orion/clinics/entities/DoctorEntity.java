package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "doctors_specialties",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_name")
    )
    private Set<SpecialtyEntity> specialties;

    @ManyToMany(mappedBy = "doctors")
    private List<ClinicEntity> clinics;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorRecordEntity> records;
}
