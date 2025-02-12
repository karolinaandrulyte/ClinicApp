package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clinic_records")
public class ClinicRecordEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "status_name")
        private ClinicStatusEntity status;

        @Column(name = "updated", nullable = false)
        private LocalDateTime updated;

        @ManyToOne
        @JoinColumn(name = "clinic_id")
        private ClinicEntity clinic;
}