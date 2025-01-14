package com.orion.clinics.repositories;

import com.orion.clinics.entities.ClinicRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRecordRepository extends JpaRepository<ClinicRecordEntity, Long> {
}
