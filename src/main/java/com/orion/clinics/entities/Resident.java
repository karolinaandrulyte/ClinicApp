package com.orion.clinics.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RESIDENT")
public class Resident extends DoctorEntity {

}