package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "doctor_records")
public class DoctorRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime updated;

    @ManyToMany(mappedBy = "doctorRecords")
    private Set<DoctorEntity> doctors;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private DoctorStatusEntity status;
}
