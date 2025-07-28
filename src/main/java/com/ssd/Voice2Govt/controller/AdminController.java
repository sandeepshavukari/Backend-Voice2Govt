package com.ssd.Voice2Govt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssd.Voice2Govt.dto.AdminDto;
import com.ssd.Voice2Govt.dto.CitizenDto;
import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.Admin;
import com.ssd.Voice2Govt.service.AdminService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/register")
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        AdminDto createdAdmin = adminService.createAdmin(adminDto);
        return ResponseEntity.ok(createdAdmin);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto adminDto = adminService.getAdminById(id);
        return ResponseEntity.ok(adminDto);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<AdminDto> getAdminByUsername(@PathVariable String username) {
        AdminDto adminDto = adminService.getAdminByUsername(username);
        if (adminDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(adminDto);
    }
    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> adminList = adminService.getAllAdmins();
        return ResponseEntity.ok(adminList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id, @RequestBody AdminDto updatedAdmin) {
        AdminDto adminDto = adminService.updateAdmin(id, updatedAdmin);
        return ResponseEntity.ok(adminDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDto adminDto) {
        AdminDto authenticatedAdminDto = adminService.authenticateAdmin(adminDto.getAdmUsername(), adminDto.getAdmPassword());

        if (authenticatedAdminDto != null) {
            return ResponseEntity.ok(authenticatedAdminDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin username or password."); // Specific error message
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @PostMapping("/citizens/register")
    public ResponseEntity<CitizenDto> createCitizen(@RequestBody CitizenDto citizenDto) {
        CitizenDto createdCitizen = adminService.createCitizen(citizenDto);
        return ResponseEntity.ok(createdCitizen);
    }

    @GetMapping("/citizens/id/{id}")
    public ResponseEntity<CitizenDto> getCitizenById(@PathVariable Long id) {
        CitizenDto citizenDto = adminService.getCitizenById(id);
        return ResponseEntity.ok(citizenDto);
    }
    @GetMapping("/citizens")
    public ResponseEntity<List<CitizenDto>> getAllCitizens() {
        List<CitizenDto> citizenList = adminService.getAllCitizens();
        return ResponseEntity.ok(citizenList);
    }
    @PutMapping("/citizens/{id}")
    public ResponseEntity<CitizenDto> updateCitizen(@PathVariable Long id, @RequestBody CitizenDto updatedCitizen) {
        CitizenDto citizenDto = adminService.updateCitizen(id, updatedCitizen);
        return ResponseEntity.ok(citizenDto);
    }
    @DeleteMapping("/citizens/{id}")
    public ResponseEntity<String> deleteCitizen(@PathVariable Long id) {
        adminService.deleteCitizen(id);
        return ResponseEntity.ok("Citizen deleted successfully.");
    }
    @PostMapping("/politicians/create")
    public ResponseEntity<PoliticianDto> createPolitician(@RequestBody PoliticianDto politicianDto) {
        PoliticianDto createdPolitician = adminService.createPolitician(politicianDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPolitician);
    }
    @GetMapping("/politicians/id/{id}")
    public ResponseEntity<PoliticianDto> getPoliticianById(@PathVariable Long id) {
        PoliticianDto politicianDto = adminService.getPoliticianById(id);
        return ResponseEntity.ok(politicianDto);
    }
    @GetMapping("/politicians")
    public ResponseEntity<List<PoliticianDto>> getAllPoliticians() {
        List<PoliticianDto> politicians = adminService.getAllPoliticians();
        return ResponseEntity.ok(politicians);
    }

    @PutMapping("/politicians/{id}")
    public ResponseEntity<PoliticianDto> updatePolitician(@PathVariable Long id, @RequestBody PoliticianDto updatedPolitician) {
        PoliticianDto politicianDto = adminService.updatePolitician(id, updatedPolitician);
        return ResponseEntity.ok(politicianDto);
    }

    @DeleteMapping("/politicians/{id}")
    public ResponseEntity<String> deletePolitician(@PathVariable Long id) {
        adminService.deletePolitician(id);
        return ResponseEntity.ok("Politician deleted successfully.");
    }
}