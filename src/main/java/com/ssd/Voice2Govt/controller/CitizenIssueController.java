package com.ssd.Voice2Govt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // Import DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssd.Voice2Govt.dto.CitizenIssueDto;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import com.ssd.Voice2Govt.mapper.CitizenIssueMapper;
import com.ssd.Voice2Govt.service.CitizenIssueService;

@RestController
@RequestMapping("/api/citizenissues")
public class CitizenIssueController {

	private final CitizenIssueService citizenIssueService;

	public CitizenIssueController(CitizenIssueService citizenIssueService) {
		this.citizenIssueService = citizenIssueService;
	}

	@PostMapping("/create")
	public ResponseEntity<CitizenIssueDto> createIssue(
			@RequestParam("citizenUsername") String citizenUsername,
			@RequestParam("issueTitle") String issueTitle,
			@RequestParam("issueDescription") String issueDescription,
			@RequestParam(value = "issueImage", required = false) MultipartFile issueImage) {

		byte[] imageData = null;
		try {
			if (issueImage != null && !issueImage.isEmpty()) {
				imageData = issueImage.getBytes();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

		CitizenIssue savedIssue = citizenIssueService.createIssue(citizenUsername, issueTitle, issueDescription, imageData);
		return ResponseEntity.ok(CitizenIssueMapper.mapToCitizenIssueDto(savedIssue));
	}

	@GetMapping("/citizen/{ctiId}")
	public ResponseEntity<List<CitizenIssueDto>> getCitizenIssuesByCtiId(@PathVariable Long ctiId) {
		List<CitizenIssue> issues = citizenIssueService.getIssuesByCitizenCtiId(ctiId);
		if (issues.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<CitizenIssueDto> issueDtos = issues.stream()
				.map(CitizenIssueMapper::mapToCitizenIssueDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(issueDtos);
	}

	@GetMapping("/citizen/view/{username}")
	public ResponseEntity<List<CitizenIssueDto>> getIssuesByCitizenUsername(@PathVariable String username) {
		List<CitizenIssue> issues = citizenIssueService.getIssuesByCitizenUsername(username);
		List<CitizenIssueDto> issueDtos = issues.stream()
				.map(CitizenIssueMapper::mapToCitizenIssueDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(issueDtos);
	}

	@GetMapping
	public ResponseEntity<List<CitizenIssueDto>> getAllCitizenIssues() {
		List<CitizenIssue> issues = citizenIssueService.getAllCitizenIssues();
		List<CitizenIssueDto> issueDtos = issues.stream()
				.map(CitizenIssueMapper::mapToCitizenIssueDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(issueDtos);
	}
	@DeleteMapping("/{issueId}")
	public ResponseEntity<String> deleteCitizenIssue(@PathVariable Long issueId) {
		citizenIssueService.deleteCitizenIssue(issueId); // Call service method
		return ResponseEntity.ok("Issue deleted successfully.");
	}
	// --- END NEW ---
}