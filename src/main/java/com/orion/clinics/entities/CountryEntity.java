package com.orion.clinics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class CountryEntity {

    @Id
    private String name;

    @OneToMany(mappedBy = "country")
    private List<CityEntity> cities;
}
