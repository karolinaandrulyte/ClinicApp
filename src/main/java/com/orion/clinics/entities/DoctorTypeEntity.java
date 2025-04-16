package com.orion.clinics.entities;

import com.orion.clinics.enums.DoctorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "doctor_types")
public class DoctorTypeEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private DoctorType type;

    @Column(name = "description", nullable = false)
    private String description;
}