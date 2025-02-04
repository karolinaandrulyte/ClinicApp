package com.orion.clinics.repositories;

import com.orion.clinics.entities.SpecialtyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, String> {
    Optional<SpecialtyEntity> findByName(String name);
}