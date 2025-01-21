package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DocumentTypeDTO;
import com.orion.clinics.entities.DocumentTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    DocumentTypeDTO toDocumentTypeDTO(DocumentTypeEntity documentTypeEntity);
    DocumentTypeEntity toDocumentTypeEntity(DocumentTypeDTO documentTypeDTO);
}
