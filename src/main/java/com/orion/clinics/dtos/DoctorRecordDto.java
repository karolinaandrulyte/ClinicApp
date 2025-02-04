package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DoctorRecordDto {
    private Long id;
    private LocalDateTime updated;
    private RecordStatusDto status;
    private Long doctorId;
}
