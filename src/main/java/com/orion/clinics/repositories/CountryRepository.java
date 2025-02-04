package com.orion.clinics.repositories;

import com.orion.clinics.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    Optional<CountryEntity> findByIsoCode(String isoCode);
}
