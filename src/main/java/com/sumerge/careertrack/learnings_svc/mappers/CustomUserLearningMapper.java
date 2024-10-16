package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.requests.CustomUserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomUserLearningMapper {

    @Mapping(source = "type" , target="type")
    @Mapping(source = "subject" ,target="subject")
    @Mapping(source = "title" , target="title")
    @Mapping(source = "url" ,target="url")
    @Mapping(source = "description" ,target="description")
    @Mapping(source = "lengthInHours" ,target="lengthInHours")
    @Mapping(source = "approved" , target = "approved")
    LearningRequestDTO toLearningRequestDTO(CustomUserLearningRequestDTO requestDTO);

    @Mapping(source = "proof" ,target="proof")
    @Mapping(source = "comment" ,target="comment")
    @Mapping(source = "date" ,target="date")
    @Mapping(source = "userId" , target="userId")
    UserLearningRequestDTO toUserLearningRequestDTO(CustomUserLearningRequestDTO requestDTO);



}
