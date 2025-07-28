package com.ssd.Voice2Govt.controller;

import com.ssd.Voice2Govt.dto.AdminDto; // Not used, can remove if not needed elsewhere
import com.ssd.Voice2Govt.dto.CitizenDto;
import com.ssd.Voice2Govt.dto.IssueDto; // Not used, can remove if not needed elsewhere
import com.ssd.Voice2Govt.entity.Admin; // Not used, can remove if not needed elsewhere
import com.ssd.Voice2Govt.entity.Citizen;
import com.ssd.Voice2Govt.entity.CitizenIssue; // Not used, can remove if not needed elsewhere
import com.ssd.Voice2Govt.mapper.CitizenMapper; // Not directly used in this controller, but in service
import com.ssd.Voice2Govt.service.CitizenIssueService; // Not used in methods shown, but injected
import com.ssd.Voice2Govt.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private CitizenIssueService citizenIssueService;

    @PostMapping("/register")
    public ResponseEntity<CitizenDto> createCitizen(@RequestBody CitizenDto citizenDto) {
        CitizenDto createdCitizen = citizenService.createCitizen(citizenDto);
        return new ResponseEntity<>(createdCitizen, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCitizen(@RequestBody CitizenDto citizenDto) {
        CitizenDto authenticatedCitizenDto = citizenService.authenticateCitizen(citizenDto.getCtiUsername(), citizenDto.getCtiPassword());
        if (authenticatedCitizenDto != null) {
            return ResponseEntity.ok(authenticatedCitizenDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid citizen username or password.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitizenDto> updateCitizen(@PathVariable Long id, @RequestBody CitizenDto updatedCitizen) {
        CitizenDto citizenDto = citizenService.updateCitizen(id, updatedCitizen);
        return ResponseEntity.ok(citizenDto);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<CitizenDto> getCitizenById(@PathVariable Long id) {
        CitizenDto citizenDto = citizenService.getCitizenById(id);
        if (citizenDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(citizenDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CitizenDto> getCitizenByUsername(@PathVariable String username) {
        CitizenDto citizenDto = citizenService.getCitizenByUsername(username);
        if (citizenDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(citizenDto);
    }
}