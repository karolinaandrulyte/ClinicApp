package com.orion.clinics.enums;

import com.orion.clinics.exception.MissingSpecialtyException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClinicsAppErrors {
    INTERNAL_ERROR(100, "An unexpected error occurred", Exception.class),
    MISSING_SPECIALTY(101, "Missing specialty",MissingSpecialtyException.class),
    ENTITY_NOT_FOUND(102, "Entity not found {}", EntityNotFoundException.class),
    INVALID_ARGUMENT(103, "Invalid argument", IllegalArgumentException.class);

    private final int code;
    private final String message;
    private final Class<? extends Exception> exceptionClass;

    public static ClinicsAppErrors fromException(Exception ex) {
        for (ClinicsAppErrors error : values()) {
            if (error.exceptionClass.isInstance(ex)) {
                return error;
            }
        }
        return INTERNAL_ERROR;
    }
}