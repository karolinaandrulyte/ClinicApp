package com.orion.clinics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentDto {
    private Long id;
    private String type;
    private byte[] content;
    private Long clinicId;
}