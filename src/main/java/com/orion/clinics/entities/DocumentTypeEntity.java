package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "document_types")
public class DocumentTypeEntity {

    @Id
    private String type;

    @OneToMany(mappedBy = "type")
    private List<DocumentEntity> documents;
}
