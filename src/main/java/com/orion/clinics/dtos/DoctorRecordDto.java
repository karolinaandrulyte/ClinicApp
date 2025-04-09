package com.orion.clinics.dtos;

import com.orion.clinics.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRecordDto {
    private Long id;
    private Long doctorId;
    private Long clinicId;
    private RecordStatus status;
    private LocalDate statusStartDate;
    private LocalDate statusEndDate;
    private LocalDateTime updated;
}