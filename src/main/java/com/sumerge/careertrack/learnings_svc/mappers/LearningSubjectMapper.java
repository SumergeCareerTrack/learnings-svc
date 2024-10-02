package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LearningSubjectMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "name", source = "name")
  LearningSubject toLearningSubject(LearningSubjectRequestDTO learningDTO);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type",  source = "learning.type")
    @Mapping(target = "name", source = "name")
  LearningSubjectResponseDTO toLearningSubjectDTO(LearningSubject learning);
}
