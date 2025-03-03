package com.orion.clinics.controllers;

import com.orion.clinics.dtos.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.orion.clinics.enums.DocumentType.CONSENT_FORM;

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
        return new RecordStatusDto("dummy status");
    }

    @GetMapping("/clinics-dummy")
    public ClinicDto getDummyClinic() {
        return new ClinicDto(null, "Dummy Clinic", "1234 Clinic St", 5L);
    }

    @GetMapping("/doctors-dummy")
    public DoctorDto getDummyDoctor() {
        Date dateOfBirth = Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        Set<SpecialtyDto> specialties = Set.of(
                new SpecialtyDto("cardiology"),
                new SpecialtyDto("neurology"));
        List<ClinicDto> clinics = List.of(
                new ClinicDto(),
                new ClinicDto()
        );
        return new DoctorDto(null, "First", "Last", dateOfBirth, "1234 Doctor St", "1234567890", "doctor@example.com", specialties, clinics);
    }

    @GetMapping("/doctorRecords-dummy")
    public DoctorRecordDto getDummyDoctorRecord() {
        DoctorRecordDto doctorRecordDto = new DoctorRecordDto();
        doctorRecordDto.setUpdated(LocalDateTime.parse("2025-02-06T10:00:00"));
        doctorRecordDto.setStatus("employed");
        doctorRecordDto.setDoctorId(14L);
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