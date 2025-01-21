package com.orion.clinics.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "countries")
public class CountryEntity {

    @Id
    @Column(name = "iso_code", nullable = false)
    private String isoCode;

    @Column(name = "name", nullable = false)
    private String name;
}
