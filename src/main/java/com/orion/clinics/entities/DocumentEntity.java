package com.orion.clinics.entities;

import com.orion.clinics.helpers.DocumentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DocumentType type;

    @Lob
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private ClinicEntity clinic;
}

