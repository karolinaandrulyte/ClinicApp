package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_name", referencedColumnName = "type")
    private DocumentTypeEntity type;

    @Lob
    private Blob content;

    @ManyToMany(mappedBy = "documents")
    private List<ClinicEntity> clinics;
}

