package com.orion.clinics.repositories;

import com.orion.clinics.entities.DoctorStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorStatusRepository extends JpaRepository<DoctorStatusEntity, Long> {
}
