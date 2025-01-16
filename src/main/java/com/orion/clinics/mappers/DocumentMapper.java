package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DocumentDTO;
import com.orion.clinics.entities.DocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDTO toDocumentDTO(DocumentEntity documentEntity);
    DocumentEntity toDocumentEntity(DocumentDTO documentDTO);
}

