package com.orion.clinics.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DoctorType {
    INTERN("Intern"),
    RESIDENT("Resident"),
    CHIEF("Chief of department");

    private final String description;

    public String getDescription() {
        return description;
    }
}