package com.ssd.Voice2Govt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import com.ssd.Voice2Govt.entity.Politician;
import com.ssd.Voice2Govt.repository.PoliticianRepository;
import com.ssd.Voice2Govt.service.PoliticianService;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	private final PoliticianService politicianService;
	private final PoliticianRepository politicianRepository;

	@Autowired
	public PoliticianController(PoliticianService politicianService, PoliticianRepository politicianRepository) {
		this.politicianService = politicianService;
		this.politicianRepository = politicianRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<PoliticianDto> createPolitician(@RequestBody PoliticianDto politicianDto) {
		PoliticianDto createdPolitician = politicianService.createPolitician(politicianDto);
		return ResponseEntity.ok(createdPolitician);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginPolitician(@RequestBody PoliticianDto politicianDto) {
		PoliticianDto politician = politicianService.loginPolitician(politicianDto.getPolUsername(), politicianDto.getPolPassword());
		if (politician != null) {
			return ResponseEntity.ok(politician);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid politician username or password.");
		}
	}

	@GetMapping("/{username}/issues")
	public ResponseEntity<List<CitizenIssue>> getCitizenIssuesByPolitician(@PathVariable String username) {
		Optional<Politician> politicianOptional = politicianRepository.findByPolUsername(username);
		if (politicianOptional.isPresent()) {
			Politician politician = politicianOptional.get();
			List<CitizenIssue> issues = politicianService.getCitizenIssuesForPolitician(politician.getPolConstituency());
			return ResponseEntity.ok(issues);
		}
		return ResponseEntity.status(404).body(null);
	}

	@PutMapping("/{politicianId}/issues/{issueId}/status")
	public ResponseEntity<String> updateIssueStatus(
			@PathVariable Long politicianId,
			@PathVariable Long issueId,
			@RequestBody String status) {
		try {
			politicianService.updateIssueStatus(politicianId, issueId, status);
			return ResponseEntity.ok("Issue status updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
	@GetMapping("/{politicianId}/issues/inprogress")
	public List<CitizenIssue> getInProgressIssues(@PathVariable Long politicianId) {
		return politicianService.getInProgressIssuesForPolitician(politicianId);
	}
	@GetMapping("/{politicianId}/issues/resolved")
	public List<CitizenIssue> getResolvedIssues(@PathVariable Long politicianId) {
		return politicianService.getResolvedIssuesForPolitician(politicianId);
	}
	@GetMapping("/{politicianId}/issues/pending")
	public List<CitizenIssue> getPendingIssues(@PathVariable Long politicianId) {
		return politicianService.getPendingIssuesForPolitician(politicianId);
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<PoliticianDto> getPoliticianById(@PathVariable Long id) {
		PoliticianDto politicianDto = politicianService.getPoliticianById(id);
		if (politicianDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(politicianDto);
	}
	@GetMapping("/username/{username}")
	public ResponseEntity<PoliticianDto> getPoliticianByUsername(@PathVariable String username) {
		PoliticianDto politicianDto = politicianService.getPoliticianByUsername(username);
		if (politicianDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(politicianDto);
	}
}