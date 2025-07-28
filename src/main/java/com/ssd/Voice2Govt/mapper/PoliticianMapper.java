package com.ssd.Voice2Govt.mapper;

import com.ssd.Voice2Govt.dto.PoliticianDto;
import com.ssd.Voice2Govt.entity.Politician;

public class PoliticianMapper {

    public static PoliticianDto mapToPoliticianDto(Politician politician) {
        if (politician == null) {
            return null;
        }

        return new PoliticianDto(
                politician.getPol_id(),
                politician.getPol_firstName(),
                politician.getPol_lastName(),
                politician.getPol_email(),
                politician.getPol_phoneNumber(),
                politician.getPol_dob(),
                politician.getPolUsername(),
                politician.getPolPassword(),
                politician.getPolConstituency()
        );
    }

    public static Politician mapToPolitician(PoliticianDto politicianDto) {
        if (politicianDto == null) {
            return null;
        }

        return new Politician(
                politicianDto.getPol_id(),
                politicianDto.getPol_firstName(),
                politicianDto.getPol_lastName(),
                politicianDto.getPol_email(),
                politicianDto.getPol_phoneNumber(),
                politicianDto.getPol_dob(),
                politicianDto.getPolUsername(),
                politicianDto.getPolPassword(),
                politicianDto.getPolConstituency()
        );
    }
}