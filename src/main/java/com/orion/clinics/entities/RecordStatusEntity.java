package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "record_status")
public class RecordStatusEntity {

    @Id
    private String status;

    @OneToMany(mappedBy = "status")
    private List<DoctorRecordEntity> doctorRecords;
}
