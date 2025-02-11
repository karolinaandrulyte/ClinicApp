package com.orion.clinics.enums;

import com.orion.clinics.exception.ApiException;
import com.orion.clinics.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClinicsAppErrors {
    INTERNAL_ERROR(100, "An unexpected error occurred", Exception.class),
    INVALID_ARGUMENT(101, "Invalid argument", IllegalArgumentException.class),
    ENTITY_NOT_FOUND(102, "Entity not found", ResourceNotFoundException.class);

    private final int code;
    private final String message;

    private final Class<? extends Exception> exceptionClass;

    public static ClinicsAppErrors fromException(Exception ex) {
        if (ex instanceof ApiException apiEx) {
                return apiEx.getError();
        }
        for (ClinicsAppErrors error : values()) {
            if (error.exceptionClass.isInstance(ex)) {
                return error;
            }
        }
        return INTERNAL_ERROR;
    }
}