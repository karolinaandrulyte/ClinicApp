package com.orion.clinics.exception;

public class MissingSpecialtyException extends RuntimeException {
    public MissingSpecialtyException(String message) {
        super(message);
    }
}