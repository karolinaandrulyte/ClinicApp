package com.orion.clinics.entities;

import com.orion.clinics.enums.NameUtils;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(NameUtils.RESIDENT)
public class Resident extends DoctorEntity {

}
