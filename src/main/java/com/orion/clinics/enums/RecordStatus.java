package com.orion.clinics.enums;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public enum RecordStatus {
    EMPLOYED("Employed"),
    UNEMPLOYED("Unemployed"),
    MATERNITY_LEAVE("Maternity Leave"),
    VACATION("Vacation");

    private final String description;
    // to use in methods
    public final static List<RecordStatus> ACTIVITY_RECORD_STATUSES = List.of(EMPLOYED);
    public final static List<RecordStatus> INACTIVITY_RECORD_STATUSES = List.of(UNEMPLOYED, MATERNITY_LEAVE, VACATION);

    public String getDescription() {
        return description;
    }
}