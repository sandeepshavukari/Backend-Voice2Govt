package com.ssd.Voice2Govt.service;

import java.util.List;

import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import com.ssd.Voice2Govt.entity.Politician;

public interface PoliticianService {
	 PoliticianDto createPolitician(PoliticianDto politicianDto);
	 PoliticianDto loginPolitician(String username, String password);
	 List<CitizenIssue> getCitizenIssuesForPolitician(String constituency);
	 public void updateIssueStatus(Long politicianId, Long issueId, String status);
	 List<CitizenIssue> getInProgressIssuesForPolitician(Long politicianId);
	 List<CitizenIssue> getResolvedIssuesForPolitician(Long politicianId);
	 List<CitizenIssue> getPendingIssuesForPolitician(Long politicianId);
	PoliticianDto getPoliticianById(Long id);
	PoliticianDto getPoliticianByUsername(String username);


}
