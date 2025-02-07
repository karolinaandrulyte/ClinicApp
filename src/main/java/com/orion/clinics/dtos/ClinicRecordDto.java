package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicRecordDto {
    private Long id;
    private String statusName;
    private LocalDateTime updated;
    private Long clinicId;
}