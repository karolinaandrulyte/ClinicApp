package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private RecordStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
