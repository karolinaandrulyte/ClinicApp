package com.orion.clinics.controllers;

import com.orion.clinics.dtos.*;
import com.orion.clinics.enums.DoctorType;
import com.orion.clinics.enums.RecordStatus;
import lombok.Builder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static com.orion.clinics.enums.DocumentType.CONSENT_FORM;

@Builder
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/countries-dummy")
    public CountryDto getDummyCountry() {
        return new CountryDto("DUM", "DummyCountry");
    }

    @GetMapping("/cities-dummy")
    public CityDto getDummyCity() {
        return new CityDto(null, "DummyCity", "LV");
    }

    @GetMapping("/specialties-dummy")
    public SpecialtyDto getDummySpecialty() {
        return new SpecialtyDto("dummy specialist");
    }

    @GetMapping("/clinicStatus-dummy")
    public ClinicStatusDto getDummyClinicStatus() {
        return new ClinicStatusDto("dummy status");
    }

    @GetMapping("/recordStatus-dummy")
    public RecordStatusDto getDummyRecordStatus() {
        return new RecordStatusDto("dummy status", "dummy description");
    }

    @GetMapping("/clinics-dummy")
    public ClinicDto getDummyClinic() {
        return new ClinicDto(null, "Dummy Clinic", "1234 Clinic St", 5L);
    }

    @GetMapping("/doctors-dummy-create")
    public DoctorCreateDto getDummyCreateDto() {
        return DoctorCreateDto.builder()
                .clinicIds(Set.of(6L, 7L))
                .specialtyNames(Set.of("cardiology", "dermatology"))
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1980, 6, 15))
                .address("123 Elm St")
                .phoneNumber("+1234567890")
                .email("john.doe@example.com")
                .doctorType(DoctorType.INTERN)
                .build();
    }

    @GetMapping("/doctorRecords-dummy")
    public DoctorRecordDto getDummyDoctorRecord() {
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto();
        doctorRecordDto.setStatus(RecordStatus.EMPLOYED);
        doctorRecordDto.setDoctorId(14L);
        doctorRecordDto.setClinicId(6L);
        doctorRecordDto.setStatusStartDate(LocalDate.parse("2025-04-08"));
        doctorRecordDto.setStatusEndDate(LocalDate.parse("2026-04-07"));
        return doctorRecordDto;
    }

    @GetMapping("/clinicRecords-dummy")
    public ClinicRecordDto getDummyClinicRecord() {
        ClinicRecordDto clinicRecordDto = new ClinicRecordDto();
        clinicRecordDto.setUpdated(LocalDateTime.parse("2025-02-06T10:00:00"));
        clinicRecordDto.setStatusName("open");
        clinicRecordDto.setClinicId(6L);
        return clinicRecordDto;
    }

    @GetMapping("/documents-dummy")
    public DocumentDto getDummyDocument() {
        return new DocumentDto(
                null,
                CONSENT_FORM,
                new byte[]{1, 2, 3},
                1L
        );
    }

    @GetMapping("/documentTypes-dummy")
    public DocumentTypeDto getDummyDocumentType() {
        return new DocumentTypeDto("CONSENT_FORM", "Consent Form");
    }

}