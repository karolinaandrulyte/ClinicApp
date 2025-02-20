package com.orion.clinics.exception;

import com.orion.clinics.enums.ClinicsAppErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
// Each handler method catches a specific type of exception,
// retrieves the corresponding error from ClinicsAppErrors,
// and returns an ErrorResponse(consistent JSON responses)

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ClinicsAppErrors error = ex.getError();
        ErrorResponse errorResponse = new ErrorResponse(
                error.getCode(),
                error.getMessage() + ": " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        ClinicsAppErrors error = ClinicsAppErrors.fromException(ex);
        ErrorResponse errorResponse = new ErrorResponse(
                error.getCode(),
                error.getMessage() + ": " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ClinicsAppErrors error = ClinicsAppErrors.fromException(ex);
        ErrorResponse errorResponse = new ErrorResponse(
                error.getCode(),
                error.getMessage() + ": " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}