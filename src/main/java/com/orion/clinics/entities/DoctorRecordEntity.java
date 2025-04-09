package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "doctor_records")
public class DoctorRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private ClinicEntity clinic;

    @ManyToOne
    @JoinColumn(name = "status_name", referencedColumnName = "status", nullable = false)
    private RecordStatusEntity status;

    @Column(name = "status_start_date", nullable = false)
    private LocalDate statusStartDate;

    @Column(name = "status_end_date")
    private LocalDate statusEndDate;

    @Column(name = "updated", updatable = false)
    private LocalDateTime updated = LocalDateTime.now();
}