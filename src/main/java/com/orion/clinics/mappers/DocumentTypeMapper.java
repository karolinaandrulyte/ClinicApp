package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DocumentTypeDto;
import com.orion.clinics.entities.DocumentTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    DocumentTypeDto toDocumentTypeDto(DocumentTypeEntity documentTypeEntity);
    DocumentTypeEntity toDocumentTypeEntity(DocumentTypeDto documentTypeDto);
}
