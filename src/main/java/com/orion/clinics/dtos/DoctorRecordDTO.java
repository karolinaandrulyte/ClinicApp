package com.orion.clinics.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorRecordDTO {
    private Long id;
    private String name;
    private LocalDateTime updated;
    private RecordStatusDTO status;
    private DoctorDTO doctor;
}
