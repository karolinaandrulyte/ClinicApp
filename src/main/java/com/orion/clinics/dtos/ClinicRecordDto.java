package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClinicRecordDto {
    private Long id;
    private String statusName;
    private LocalDateTime updated;
    private Long clinicId;
}