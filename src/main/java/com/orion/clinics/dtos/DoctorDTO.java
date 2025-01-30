package com.orion.clinics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;

    @Past(message = "Invalid date. The date should be in present or past")
    private Date dateOfBirth;
    private String address;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Invalid phone number format. Use a valid 10-digit number or include a country code")
    private String phoneNumber;

    @Email(message = "The email format is incorrect. Follow: example@gmail.com")
    private String email;

    private Set<SpecialtyDTO> specialties;
    private List<ClinicDTO> clinics;
}
