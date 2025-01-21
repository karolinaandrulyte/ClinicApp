package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "doctor_records")
public class DoctorRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private RecordStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
