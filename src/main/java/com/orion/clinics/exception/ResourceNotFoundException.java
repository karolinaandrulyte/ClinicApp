package com.orion.clinics.exception;

import com.orion.clinics.enums.ClinicsAppErrors;
//Keeping it is better for readability and allows catching specific exceptions
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String details) {
        super(ClinicsAppErrors.ENTITY_NOT_FOUND, details);
    }
}