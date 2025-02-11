package com.orion.clinics.exception;

import com.orion.clinics.enums.ClinicsAppErrors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiException extends RuntimeException {
//    all exceptions inherit the same structured behavior
    ClinicsAppErrors error;
    String[] values;

    public ApiException(ClinicsAppErrors error, String details) {
        super(details);
        this.error = error;
        this.values = null;
    }

    public ApiException(ClinicsAppErrors error, String details, String... values) {
        super(details);
        this.error = error;
        this.values = values;
    }
}