package com.ssd.Voice2Govt.mapper;
import com.ssd.Voice2Govt.dto.CitizenIssueDto;
import com.ssd.Voice2Govt.entity.CitizenIssue;
import java.util.Base64;

public class CitizenIssueMapper {

    public static CitizenIssueDto mapToCitizenIssueDto(CitizenIssue issue) {
        if (issue == null) {
            return null;
        }
        String imageBase64 = issue.getIssueImage() != null ? Base64.getEncoder().encodeToString(issue.getIssueImage()) : null;
        CitizenIssueDto dto = new CitizenIssueDto();
        dto.setIssueId(issue.getIssueId());
        dto.setIssueTitle(issue.getIssueTitle());
        dto.setIssueDescription(issue.getIssueDescription());
        dto.setIssueImage(imageBase64);
        dto.setStatus(issue.getStatus());
        if (issue.getCitizen() != null) {
            dto.setCitizen(CitizenMapper.mapToCitizenDto(issue.getCitizen()));
        }
        if (issue.getPolitician() != null) {
            dto.setPolitician(PoliticianMapper.mapToPoliticianDto(issue.getPolitician()));
        }
        return dto;
    }
}