package com.ssd.Voice2Govt.service.impl;

import com.ssd.Voice2Govt.dto.CitizenDto;
import com.ssd.Voice2Govt.entity.Citizen;
import com.ssd.Voice2Govt.exception.ResourceNotFoundException;
import com.ssd.Voice2Govt.mapper.CitizenMapper; // Assuming you have this
import com.ssd.Voice2Govt.repository.CitizenRepository;
import com.ssd.Voice2Govt.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitizenServiceImpl implements CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Override
    public CitizenDto createCitizen(CitizenDto citizenDto) {
        Citizen citizen = CitizenMapper.mapToCitizen(citizenDto);
        Citizen savedCitizen = citizenRepository.save(citizen);
        return CitizenMapper.mapToCitizenDto(savedCitizen);
    }
@Override
public CitizenDto authenticateCitizen(String username, String password) {
    Optional<Citizen> citizenOptional = citizenRepository.findByCtiUsername(username);
    if (citizenOptional.isPresent()) {
        Citizen citizen = citizenOptional.get();
        if (citizen.getCtiPassword().equals(password)) {
            return CitizenMapper.mapToCitizenDto(citizen); // <--- ESSENTIAL: RETURN THE DTO
        }
    }
    return null;
}

    @Override
    public CitizenDto getCitizenById(Long id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));
        return CitizenMapper.mapToCitizenDto(citizen);
    }

    @Override
    public CitizenDto getCitizenByUsername(String username) {
        Citizen citizen = citizenRepository.findByCtiUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with username: " + username));
        return CitizenMapper.mapToCitizenDto(citizen);
    }

    @Override
    public CitizenDto updateCitizen(Long id, CitizenDto updatedCitizenDto) {
        Citizen existingCitizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen not found with id: " + id));
        existingCitizen.setCti_firstName(updatedCitizenDto.getCti_firstName());
        existingCitizen.setCti_lastName(updatedCitizenDto.getCti_lastName());
        existingCitizen.setCti_email(updatedCitizenDto.getCti_email());
        existingCitizen.setCti_phoneNumber(updatedCitizenDto.getCti_phoneNumber());
        existingCitizen.setCti_dob(updatedCitizenDto.getCti_dob());
        existingCitizen.setCtiUsername(updatedCitizenDto.getCtiUsername());
        existingCitizen.setCtiConstituency(updatedCitizenDto.getCtiConstituency());
        Citizen savedCitizen = citizenRepository.save(existingCitizen);
        return CitizenMapper.mapToCitizenDto(savedCitizen);
        }

}