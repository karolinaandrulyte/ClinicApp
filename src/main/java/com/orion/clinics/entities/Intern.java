package com.orion.clinics.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INTERN")
public class Intern extends DoctorEntity {

}