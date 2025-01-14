package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clinic_records")
public class ClinicRecordEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "status_name")
        private String statusName;

        private LocalDateTime updated;

        @ManyToOne
        @JoinColumn(name = "clinic_id")
        private ClinicEntity clinic;
}
