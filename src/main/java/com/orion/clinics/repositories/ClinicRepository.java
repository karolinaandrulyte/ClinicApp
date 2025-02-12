package com.orion.clinics.repositories;

import com.orion.clinics.entities.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<ClinicEntity, Long> {
}