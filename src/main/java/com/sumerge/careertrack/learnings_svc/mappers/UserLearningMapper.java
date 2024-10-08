package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserLearningMapper {

    @Mapping(source = "proof" , target = "proof")
    @Mapping(source = "comment" , target = "comment")
    @Mapping(source = "date" , target="date")
    @Mapping(source = "userId" ,target = "userId" )
    @Mapping(target ="learning" ,ignore=true)
    @Mapping(target = "booster" , ignore=true)
    UserLearning toUserLearning(UserLearningRequestDTO userLearningRequestDTO);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "proof" , target = "proof")
    @Mapping(source = "comment" , target = "comment")
    @Mapping(source = "date" , target="date")
    @Mapping(source = "approvalStatus" , target="approvalStatus")
    @Mapping(source = "userId", target = "userId" )
    @Mapping(source = "learning",target ="learning")
    @Mapping(source="booster", target = "booster")
    UserLearningResponseDTO toResponseDTO(UserLearning userLearning);

}
