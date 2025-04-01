package com.orion.clinics.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "doctor_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTypeEntity {

    @Id
    @Column(name = "type", nullable = false)
    private String type;
}