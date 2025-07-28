package com.ssd.Voice2Govt.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssd.Voice2Govt.entity.Citizen;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import com.ssd.Voice2Govt.entity.Politician;
import com.ssd.Voice2Govt.exception.ResourceNotFoundException; // Import if not already
import com.ssd.Voice2Govt.repository.CitizenIssueRepository;
import com.ssd.Voice2Govt.repository.CitizenRepository;
import com.ssd.Voice2Govt.repository.PoliticianRepository;
import com.ssd.Voice2Govt.service.CitizenIssueService;


@Service
public class CitizenIssueServiceImpl implements CitizenIssueService {

	@Autowired
	private CitizenIssueRepository citizenIssueRepository;
	@Autowired
	private CitizenRepository citizenRepository;
	@Autowired
	private PoliticianRepository politicianRepository;

	@Autowired
	public CitizenIssueServiceImpl(CitizenRepository citizenRepository, PoliticianRepository politicianRepository,
								   CitizenIssueRepository citizenIssueRepository) {
		this.citizenRepository = citizenRepository;
		this.politicianRepository = politicianRepository;
		this.citizenIssueRepository = citizenIssueRepository;
	}

	@Override
	public CitizenIssue createIssue(String citizenUsername, String issueTitle, String issueDescription, byte[] issueImage) {
		Citizen citizen = citizenRepository.findByCtiUsername(citizenUsername)
				.orElseThrow(() -> new RuntimeException("Citizen not found"));

		List<Politician> politicians = politicianRepository.findByPolConstituency(citizen.getCtiConstituency());
		if (politicians.isEmpty()) {
			throw new RuntimeException("No politician found for this constituency");
		}

		Politician politician = politicians.get(0);

		CitizenIssue issue = new CitizenIssue();
		issue.setCitizen(citizen);
		issue.setPolitician(politician);
		issue.setIssueTitle(issueTitle);
		issue.setIssueDescription(issueDescription);
		issue.setStatus("Pending");
		issue.setIssueImage(issueImage);

		return citizenIssueRepository.save(issue);
	}

	@Override
	public List<CitizenIssue> getIssuesByCitizenCtiId(Long ctiId) {
		return citizenIssueRepository.findByCitizenCtiId(ctiId);
	}

	@Override
	public List<CitizenIssue> getIssuesByCitizenUsername(String username) {
		return citizenIssueRepository.findByCitizenUsername(username);
	}

	@Override
	public List<CitizenIssue> getAllCitizenIssues() {
		return citizenIssueRepository.findAll();
	}
	@Override
	public void deleteCitizenIssue(Long issueId) {
		CitizenIssue issue = citizenIssueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found with ID: " + issueId));
		citizenIssueRepository.delete(issue);
	}
}