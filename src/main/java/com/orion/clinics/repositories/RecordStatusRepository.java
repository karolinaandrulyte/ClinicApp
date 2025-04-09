package com.orion.clinics.repositories;

import com.orion.clinics.entities.RecordStatusEntity;
import com.orion.clinics.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordStatusRepository extends JpaRepository<RecordStatusEntity, RecordStatus> {
    Optional<RecordStatusEntity> findByStatus(RecordStatus status);
    boolean existsByStatus(String status);
}
