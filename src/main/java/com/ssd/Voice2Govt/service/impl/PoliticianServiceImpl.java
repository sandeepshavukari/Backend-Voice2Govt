package com.ssd.Voice2Govt.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import com.ssd.Voice2Govt.entity.Politician;
import com.ssd.Voice2Govt.exception.ResourceNotFoundException; // Import ResourceNotFoundException
import com.ssd.Voice2Govt.mapper.PoliticianMapper;
import com.ssd.Voice2Govt.repository.CitizenIssueRepository;
import com.ssd.Voice2Govt.repository.PoliticianRepository;
import com.ssd.Voice2Govt.service.PoliticianService;

@Service
public class PoliticianServiceImpl implements PoliticianService {

	@Autowired
	private PoliticianRepository politicianRepository;
	@Autowired
	private CitizenIssueRepository citizenIssueRepository;


	@Override
	public PoliticianDto createPolitician(PoliticianDto politicianDto) {
		Politician politician = PoliticianMapper.mapToPolitician(politicianDto);
		Politician savedPolitician = politicianRepository.save(politician);
		return PoliticianMapper.mapToPoliticianDto(savedPolitician);
	}

	@Override
	public PoliticianDto loginPolitician(String username, String password) {
		Optional<Politician> politicianOptional = politicianRepository.findByPolUsername(username);
		if (politicianOptional.isPresent()) {
			Politician politician = politicianOptional.get();
			if (politician.getPolPassword().equals(password)) {
				return PoliticianMapper.mapToPoliticianDto(politician);
			}
		}
		return null;
	}

	@Override
	public List<CitizenIssue> getCitizenIssuesForPolitician(String constituency) {
		return citizenIssueRepository.findByPoliticianPolConstituency(constituency);
	}

	@Override
	public void updateIssueStatus(Long politicianId, Long issueId, String status) {
		Politician politician = politicianRepository.findById(politicianId)
				.orElseThrow(() -> new ResourceNotFoundException("Politician not found"));
		CitizenIssue issue = citizenIssueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if (!issue.getPolitician().getPol_id().equals(politicianId)) {
			throw new RuntimeException("Politician is not authorized to update this issue");
		}
		issue.setStatus(status);
		citizenIssueRepository.save(issue);
	}

	@Override
	public List<CitizenIssue> getInProgressIssuesForPolitician(Long politicianId) {
		return citizenIssueRepository.findByPoliticianAndStatus(politicianId, "In-Progress");
	}

	@Override
	public List<CitizenIssue> getResolvedIssuesForPolitician(Long politicianId) {
		return citizenIssueRepository.findByPoliticianAndStatus(politicianId, "Resolved");
	}

	@Override
	public List<CitizenIssue> getPendingIssuesForPolitician(Long politicianId) {
		return citizenIssueRepository.findByPoliticianAndStatus(politicianId, "Pending");
	}

	@Override
	public PoliticianDto getPoliticianById(Long id) {
		Politician politician = politicianRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Politician not found with id: " + id));
		return PoliticianMapper.mapToPoliticianDto(politician);
	}

	@Override
	public PoliticianDto getPoliticianByUsername(String username) {
		Politician politician = politicianRepository.findByPolUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Politician not found with username: " + username));
		return PoliticianMapper.mapToPoliticianDto(politician);
	}
}