// In CitizenIssueService.java (interface)
package com.ssd.Voice2Govt.service;

import com.ssd.Voice2Govt.entity.CitizenIssue;
import java.util.List;

public interface CitizenIssueService {
	CitizenIssue createIssue(String citizenUsername, String issueTitle, String issueDescription, byte[] issueImage);
	List<CitizenIssue> getIssuesByCitizenCtiId(Long ctiId);
	List<CitizenIssue> getIssuesByCitizenUsername(String username);
	List<CitizenIssue> getAllCitizenIssues();
	void deleteCitizenIssue(Long issueId);
}