package com.orion.clinics.dtos;

import com.orion.clinics.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentDto {
    private Long id;
    private DocumentType type;
    private byte[] content;
    private Long clinicId;
}