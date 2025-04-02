package com.orion.clinics.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecordStatus {
    EMPLOYED("Employed"),
    UNEMPLOYED("Unemployed");

    private final String description;

    public String getDescription() {
        return description;
    }
}