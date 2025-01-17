package com.orion.clinics.dtos;

import com.orion.clinics.helpers.DocumentType;
import lombok.Data;

import java.util.List;

@Data
public class DocumentDTO {
    private Long id;
    private DocumentType type;
    private byte[] content;
    private ClinicDTO clinic;
}
