package com.orion.clinics.enums;

import com.orion.clinics.exception.ApiException;
import com.orion.clinics.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ClinicsAppErrors {
    INTERNAL_ERROR(100, "An unexpected error occurred", Exception.class),
    INVALID_ARGUMENT(101, "Invalid argument", IllegalArgumentException.class),
    ENTITY_NOT_FOUND(102, "Entity not found", ResourceNotFoundException.class),
    INVALID_FORMAT(103, "Invalid format", HttpMessageNotReadableException.class);

    private final int code;
    private final String message;

    private final Class<? extends Exception> exceptionClass;

    public static ClinicsAppErrors fromException(Exception ex) {
        if (ex instanceof ApiException apiEx) {
                return apiEx.getError();
        }
        AtomicReference<ClinicsAppErrors> result = new AtomicReference<>(INTERNAL_ERROR);
        Stream.of(values())
                .filter(error -> !error.exceptionClass.equals(Exception.class))
                .forEach(error -> {
                    if (ex != null && error.getExceptionClass().isAssignableFrom(ex.getClass())) {
                        result.set(error);
                    }
                });
        return result.get();
    }
}