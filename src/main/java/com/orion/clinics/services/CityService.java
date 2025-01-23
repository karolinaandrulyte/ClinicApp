package com.orion.clinics.services;

import com.orion.clinics.dtos.CityDTO;
import com.orion.clinics.entities.CityEntity;
import com.orion.clinics.mappers.CityMapper;
import com.orion.clinics.mappers.CountryMapper;
import com.orion.clinics.repositories.CityRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryMapper countryMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper, CountryMapper countryMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.countryMapper = countryMapper;
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

    public CityDTO update(Long id, CityDTO cityDTO) {
        CityEntity existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
        if (cityDTO.getName() != null) {
            existingCity.setName(cityDTO.getName());
        }
        if (cityDTO.getCountry() != null) {
            existingCity.setCountry(countryMapper.toCountryEntity(cityDTO.getCountry())); // Assuming a CountryMapper exists
        }
        CityEntity updatedCity = cityRepository.save(existingCity);
        return cityMapper.toCityDTO(updatedCity);
    }

    public void deleteById(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }
}
