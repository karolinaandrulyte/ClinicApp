package com.orion.clinics.repositories;

import com.orion.clinics.entities.DocumentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, String> {
    Optional<DocumentTypeEntity> findByType(String type);
}
