package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LearningTypeMapper {
    @Mapping(target="id", ignore=true)
    LearningType toLearningType(LearningTypeRequestDTO learningDTO);
    @Mapping(target="id", source = "id")
    LearningTypeResponseDTO toLearningTypeDTO(LearningType learning);
}
