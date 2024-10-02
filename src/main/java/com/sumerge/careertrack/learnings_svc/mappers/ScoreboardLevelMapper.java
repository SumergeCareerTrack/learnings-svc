package com.sumerge.careertrack.learnings_svc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sumerge.careertrack.learnings_svc.entities.ScoreboardLevel;
import com.sumerge.careertrack.learnings_svc.entities.requests.ScoreboardLevelRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ScoreboardLevelResponseDTO;

@Mapper(componentModel = "spring")
public interface ScoreboardLevelMapper {

    @Mapping(target = "id", ignore = true)
    ScoreboardLevel toLevel(ScoreboardLevelRequestDTO dto);

    ScoreboardLevelResponseDTO toDto(ScoreboardLevel level);
}
