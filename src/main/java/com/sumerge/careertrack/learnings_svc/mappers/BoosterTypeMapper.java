package com.sumerge.careertrack.learnings_svc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sumerge.careertrack.learnings_svc.entities.BoosterType;
import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterTypeResponseDTO;

@Mapper
public interface BoosterTypeMapper {

    @Mapping(target = "id", ignore = true)
    BoosterType toBoosterType(BoosterTypeRequestDTO dto);

    BoosterTypeResponseDTO toDto(BoosterType boosterType);

}
