package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "doctor_status")
public class DoctorStatusEntity {

    @Id
    private String status;

    @OneToMany(mappedBy = "status")
    private Set<DoctorRecordEntity> doctorRecords;
}
