package com.orion.clinics.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorRecordDTO {
    private Long id;
    private LocalDateTime updated;
    private RecordStatusDTO status;
    private Long doctorId;
}
