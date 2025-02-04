package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.entities.DocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDto toDocumentDto(DocumentEntity documentEntity);
    DocumentEntity toDocumentEntity(DocumentDto documentDto);
}

