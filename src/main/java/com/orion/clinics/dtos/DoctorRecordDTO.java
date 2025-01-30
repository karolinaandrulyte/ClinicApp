package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DoctorRecordDTO {
    private Long id;
    private LocalDateTime updated;
    private RecordStatusDTO status;
    private Long doctorId;
}
