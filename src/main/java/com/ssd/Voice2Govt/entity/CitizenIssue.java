package com.ssd.Voice2Govt.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class CitizenIssue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long issueId;
	private String issueTitle;
	private String issueDescription;

	@Lob
	@Column(name = "issue_image", columnDefinition = "LONGBLOB")
	private byte[] issueImage;
	@ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER
	@JoinColumn(name = "cti_id", nullable = false)
	private Citizen citizen;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pol_id", nullable = false)
	private Politician politician;

	private String status;
}