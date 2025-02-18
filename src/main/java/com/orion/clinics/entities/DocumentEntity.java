package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "type", nullable = false)
    private DocumentTypeEntity type;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private ClinicEntity clinic;
}