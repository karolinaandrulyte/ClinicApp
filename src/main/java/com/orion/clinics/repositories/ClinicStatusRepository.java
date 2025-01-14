package com.orion.clinics.repositories;

import com.orion.clinics.entities.ClinicStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicStatusRepository extends JpaRepository<ClinicStatusEntity, Long> {
}
