package com.orion.clinics.services;

import com.orion.clinics.dtos.CityDTO;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.repositories.CityRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public List<CityDTO> findAll() {
        List<CityEntity> cities = cityRepository.findAll();
        return cities.stream()
                .map(cityMapper::toCityDTO)
                .toList();
    }

    public Optional<CityDTO> findById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        Optional<CityEntity> city = cityRepository.findById(id);
        return city.map(cityMapper::toCityDTO);
    }

    public CityDTO save(CityDTO cityDTO) {
        CityEntity city = cityMapper.toCityEntity(cityDTO);
        CityEntity savedCity = cityRepository.save(city);
        return cityMapper.toCityDTO(savedCity);
    }

    public void deleteById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }
}
