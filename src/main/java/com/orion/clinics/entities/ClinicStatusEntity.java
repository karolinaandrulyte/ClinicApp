package com.orion.clinics.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "clinic_status")
public class ClinicStatusEntity {

    @Id
    @Column(name = "status", nullable = false)
    private String status;
}
