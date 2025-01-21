package com.orion.clinics.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DocumentType {
    CONSENT_FORM("Consent Form"),
    PRESCRIPTION("Prescription"),
    LAB_REPORT("Lab Report"),
    PAYMENT_RECEIPT("Payment Receipt"),
    EMPLOYEE_RECORD("Employee Record");

    String description;
}
