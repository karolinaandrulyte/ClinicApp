package com.orion.clinics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class DoctorBaseDto {
    private String firstName;
    private String lastName;

    @Past(message = "Invalid date. The date should be in present or past")
    private LocalDate dateOfBirth;
    private String address;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Invalid phone number format. Use a valid 10-digit number or include a country code")
    private String phoneNumber;

    @Email(message = "The email format is incorrect. Follow: example@gmail.com")
    private String email;

    private String doctorType;
}