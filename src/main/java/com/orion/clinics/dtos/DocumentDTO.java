package com.orion.clinics.dtos;

import com.orion.clinics.entities.DocumentTypeEntity;
import lombok.Data;

import java.sql.Blob;

@Data
public class DocumentDTO {
    private Long id;
    private Blob content;
    private DocumentTypeEntity type;
}
