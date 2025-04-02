package com.orion.clinics.entities;

import com.orion.clinics.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "record_status")
public class RecordStatusEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = true)
    private RecordStatus status;

    @Column(name = "description", nullable = false)
    private String description;
}