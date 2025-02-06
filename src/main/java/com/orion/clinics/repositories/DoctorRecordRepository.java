package com.orion.clinics.repositories;

import com.orion.clinics.entities.DoctorRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRecordRepository extends JpaRepository<DoctorRecordEntity, Long> {
    List<DoctorRecordEntity> findByDoctorId(Long doctorId);
}
