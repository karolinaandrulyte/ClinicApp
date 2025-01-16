package com.orion.clinics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clinic_status")
public class ClinicStatusEntity {

    @Id
    private String status;
}
