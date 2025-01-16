package com.orion.clinics.controllers;

import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/countries")
//public class CountryController {
//
//    private CountryMapper countryMapper;
//    private CountryRepository countryRepository;
//
//    @Autowired
//    public CountryController(CountryMapper countryMapper, CountryRepository countryRepository) {
//        this.countryMapper = countryMapper;
//        this.countryRepository = countryRepository;
//    }
//
//    @GetMapping("/{ISO_code}")
//    public ResponseEntity<Country>
//
//
//}
