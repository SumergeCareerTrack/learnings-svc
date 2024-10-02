package com.sumerge.careertrack.learnings_svc.mappers;

import org.mapstruct.Mapper;

import com.sumerge.careertrack.learnings_svc.entities.UserScore;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserScoreResponseDTO;

@Mapper
public interface UserScoreMapper {

    public UserScoreResponseDTO toDto(UserScore userScore);

}
