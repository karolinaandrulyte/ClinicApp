package com.orion.clinics.repositories;

import com.orion.clinics.entities.ClinicStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicStatusRepository extends JpaRepository<ClinicStatusEntity, String> {
    Optional<ClinicStatusEntity> findByStatus(String status);
    boolean existsByStatus(String status);
}