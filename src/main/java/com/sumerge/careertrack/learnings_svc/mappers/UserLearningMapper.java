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
    @Mapping(source = "approvalStatus" , target="approvalStatus")
//    @Mapping(target = "user" ,ignore=true)
    @Mapping(target ="learning" ,ignore=true)
    @Mapping(target = "booster" , ignore=true)
    @Mapping(target= "proofType" , ignore=true)
    UserLearning toUserLearning(UserLearningRequestDTO userLearningRequestDTO);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "proof" , target = "proof")
    @Mapping(source = "comment" , target = "comment")
    @Mapping(source = "date" , target="date")
    @Mapping(source = "approvalStatus" , target="approvalStatus")
//    @Mapping(source = "user.id", target = "userId" )
    @Mapping(source = "learning",target ="learning")
    @Mapping(source="booster", target = "booster")
    @Mapping(source = "proofType",target= "proofType")
    UserLearningResponseDTO toResponseDTO(UserLearning userLearning);

}
