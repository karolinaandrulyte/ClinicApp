package com.orion.clinics.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "document_types")
public class DocumentTypeEntity {

    @Id
    @Column(name = "type", nullable = false)
    private String type;
}
