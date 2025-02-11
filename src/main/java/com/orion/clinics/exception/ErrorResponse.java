package com.orion.clinics.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int codeError; //get from clinicAPpErrors
    private String message;
    private LocalDateTime timestamp;
}