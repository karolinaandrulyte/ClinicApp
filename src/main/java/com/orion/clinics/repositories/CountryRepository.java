package com.orion.clinics.repositories;

import com.orion.clinics.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {
}
