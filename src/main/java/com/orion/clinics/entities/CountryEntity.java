package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "countries")
public class CountryEntity {

    @Id
    @Column(name = "iso_code", nullable = false)
    private String isoCode;

    @Column(name = "name", nullable = false)
    private String name;
}