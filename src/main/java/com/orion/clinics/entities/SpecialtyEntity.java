package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity(name = "specialties")
public class SpecialtyEntity {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public SpecialtyEntity() {
        this.name = "General Medicine";
    }
}