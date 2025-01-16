package com.orion.clinics.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClinicRecordDTO {
    private Long id;
    private String statusName;
    private LocalDateTime updated;
    private ClinicDTO clinic;
}
