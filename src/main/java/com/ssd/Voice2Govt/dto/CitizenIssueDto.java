package com.ssd.Voice2Govt.dto;

import lombok.*;

@Data
public class CitizenIssueDto {
	private Long issueId;
	private String issueTitle;
	private String citizenUsername;
	private String issueDescription;
	private String issueImage;
	private String status;
	private CitizenDto citizen;
	private PoliticianDto politician;
}