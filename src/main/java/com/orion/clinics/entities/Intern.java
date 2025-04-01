package com.orion.clinics.entities;

import com.orion.clinics.enums.NameUtils;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(NameUtils.INTERN)
public class Intern extends DoctorEntity {

}
