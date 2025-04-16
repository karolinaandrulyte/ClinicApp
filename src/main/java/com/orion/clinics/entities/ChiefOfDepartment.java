package com.orion.clinics.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CHIEF")
public class ChiefOfDepartment extends DoctorEntity{

}