package com.sumerge.careertrack.learnings_svc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sumerge.careertrack.learnings_svc.entities.Booster;
import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterResponseDTO;

@Mapper
public interface BoosterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", source = "active")
    Booster toBooster(BoosterRequestDTO dto);

    @Mapping(target = "isActive", source = "active")
    BoosterResponseDTO toDto(Booster booster);

}
