package com.orion.clinics.repositories;

import com.orion.clinics.entities.RecordStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordStatusRepository extends JpaRepository<RecordStatusEntity, Long> {
}
