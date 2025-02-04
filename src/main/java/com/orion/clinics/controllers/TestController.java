package com.orion.clinics.controllers;

import com.orion.clinics.dtos.*;
import com.orion.clinics.mappers.CityMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {

    private CityMapper cityMapper;

    public TestController(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @GetMapping("/country-dummy")
    public CountryDto getDummyCountry() {
        return new CountryDto("DUM", "DummyCountry");
    }

    @GetMapping("/city-dummy")
    public CityDto getDummyCity() {
        return new CityDto(null, "DummyCity", "LV");
    }

//    @GetMapping("/clinic-dummy")
//    public ClinicDto getDummyClinic() {
//        CityDto dummyCity = new CityDto(4L,null, null, null, null);
//        return new ClinicDto(null, "Dummy Clinic", "1234 Dummy St", dummyCity, null);
//    }

    @GetMapping("/specialty-dummy")
    public SpecialtyDto getDummySpecialty() {
        return new SpecialtyDto("dummy specialist");
    }

    @GetMapping("/doctor-dummy")
    public DoctorDto getDummyDoctor() {
        Date dateOfBirth = Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        Set<SpecialtyDto> specialties = Set.of(
                new SpecialtyDto("therapist"),
                new SpecialtyDto("general practicioner"));
        List<ClinicDto> clinics = List.of(
                new ClinicDto(3L, null, null, null, null),
                new ClinicDto(4L, null, null, null, null));
        return new DoctorDto(null, "First", "Last", dateOfBirth, "1234 Doctor St", "1234567890", "doctor@example.com", specialties, clinics);
    }
}