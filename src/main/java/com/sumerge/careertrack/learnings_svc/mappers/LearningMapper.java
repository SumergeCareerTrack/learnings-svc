package com.sumerge.careertrack.learnings_svc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;

@Mapper(componentModel = "spring")

public interface LearningMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "lengthInHours", source = "lengthInHours")
    Learning toLearning(LearningRequestDTO learningDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeName", source = "learning.type.name")
    @Mapping(target = "typeBaseScore", source = "learning.type.baseScore")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subjectName", source = "learning.subject.name")
    @Mapping(target = "subjectType", expression = "java(map(learning.getSubject()))")
    @Mapping(target = "lengthInHours", source = "lengthInHours")
    LearningResponseDTO toLearningDTO(Learning learning);

    // TODO REVIEW if the base value should be Functional or Organisational
    default String map(LearningSubject subject) {
        return subject.getType().equals(SubjectType.FUNCTIONAL) ? "Functional" : "Organizational";
    }

}
