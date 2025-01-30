package com.orion.clinics.exception;

public class SpecialtyNotFoundException extends RuntimeException {
    public SpecialtyNotFoundException(String message) {
        super(message);
    }
}
