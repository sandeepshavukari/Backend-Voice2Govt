package com.ssd.Voice2Govt.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Date createdDate;
    private Date updatedDate;
    private Long citizenId;
    private Long politicianId;
    private Long moderatorId;
}