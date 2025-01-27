package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "specialties")
public class SpecialtyEntity {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "specialties")
    private List<DoctorEntity> doctors;
}
