package com.orion.clinics.repositories;

import com.orion.clinics.entities.RecordStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordStatusRepository extends JpaRepository<RecordStatusEntity, String> {
}
