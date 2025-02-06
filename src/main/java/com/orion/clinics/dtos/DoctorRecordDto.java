package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRecordDto {
    private Long id;
    private LocalDateTime updated;
    private String status;
    private Long doctorId;
}
