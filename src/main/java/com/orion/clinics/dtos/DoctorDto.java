package com.orion.clinics.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
// for GET/read operations
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class DoctorDto extends DoctorBaseDto {
    private Long id;
    private Set<SpecialtyDto> specialties;
    private List<ClinicDto> clinics;
}