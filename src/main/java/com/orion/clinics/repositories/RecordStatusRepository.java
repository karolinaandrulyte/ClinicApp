package com.orion.clinics.repositories;

import com.orion.clinics.entities.RecordStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordStatusRepository extends JpaRepository<RecordStatusEntity, String> {
    Optional<RecordStatusEntity> findByStatus(String status);
    boolean existsByStatus(String status);
}
