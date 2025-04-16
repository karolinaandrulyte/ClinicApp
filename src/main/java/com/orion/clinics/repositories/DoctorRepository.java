package com.orion.clinics.repositories;

import com.orion.clinics.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    List<DoctorEntity> findByDoctorType_Type(String doctorType);
}