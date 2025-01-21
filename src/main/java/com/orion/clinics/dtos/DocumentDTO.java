package com.orion.clinics.dtos;

import com.orion.clinics.enums.DocumentType;
import lombok.Data;

@Data
public class DocumentDTO {
    private Long id;
    private DocumentType type;
    private byte[] content;
    private ClinicDTO clinic;
}
