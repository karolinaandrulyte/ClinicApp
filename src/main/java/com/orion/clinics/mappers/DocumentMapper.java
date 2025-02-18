package com.orion.clinics.mappers;

import com.orion.clinics.dtos.DocumentDto;
import com.orion.clinics.entities.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "type.type", target = "type")
    @Mapping(source = "clinic.id", target = "clinicId")
    DocumentDto toDocumentDto(DocumentEntity documentEntity);

    @Mapping(source = "type", target = "type.type")
    @Mapping(source = "clinicId", target = "clinic.id")
    DocumentEntity toDocumentEntity(DocumentDto documentDto);
}