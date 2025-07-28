package com.ssd.Voice2Govt.service;

import com.ssd.Voice2Govt.dto.CitizenDto;
import com.ssd.Voice2Govt.dto.IssueDto;
import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.Admin;
import com.ssd.Voice2Govt.entity.Citizen;
import com.ssd.Voice2Govt.entity.CitizenIssue;

import java.util.List;

public interface CitizenService {

    CitizenDto createCitizen(CitizenDto citizenDto);
    CitizenDto authenticateCitizen(String username, String password);
    CitizenDto getCitizenById(Long id);
    CitizenDto getCitizenByUsername(String username);
    CitizenDto updateCitizen(Long id, CitizenDto updatedCitizen);
}
