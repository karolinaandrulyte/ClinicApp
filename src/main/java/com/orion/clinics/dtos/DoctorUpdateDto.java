package com.orion.clinics.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateDto extends DoctorBaseDto {
    private Long id;
    private Set<Long> clinicIds; // for json "clinicIds": [1, 2]
    private Set<String> specialtyNames; // for json "specialtyNames": ["cardiology", "dermatology"]
}