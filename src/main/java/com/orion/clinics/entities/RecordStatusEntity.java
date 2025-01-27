package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "record_status")
public class RecordStatusEntity {

    @Id
    @Column(name = "status", nullable = false)
    private String status;
}
