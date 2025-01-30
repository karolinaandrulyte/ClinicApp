package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClinicRecordDTO {
    private Long id;
    private ClinicStatusDTO statusName;
    private LocalDateTime updated;
    private ClinicDTO clinic;
}
