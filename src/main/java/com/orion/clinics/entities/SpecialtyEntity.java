package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "specialties")
public class SpecialtyEntity {

    @Id
    private String name;

    @ManyToMany(mappedBy = "specialties")
    private List<DoctorEntity> doctors;
}
