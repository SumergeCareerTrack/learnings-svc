package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserLearningMapper {

    @Mapping(source = "proof" , target = "proof")
    @Mapping(source = "comment" , target = "comment")
    @Mapping(source = "date" , target="date")
    @Mapping(source = "approvalStatus" , target="approvalStatus")
//    @Mapping(target = "user" ,ignore=true)
//    @Mapping(target ="learning" ,ignore=true)
//    @Mapping(target = "booster" , ignore=true)
    @Mapping(source = "proofType",target= "proofType") //TODO do user need to input full object(name,id) or id only?
    UserLearning toUserLearning(UserLearningRequestDTO userLearningRequestDTO);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "proof" , target = "proof")
    @Mapping(source = "comment" , target = "comment")
    @Mapping(source = "date" , target="date")
    @Mapping(source = "approvalStatus" , target="approvalStatus")
//    @Mapping(source = "user.id", target = "userId" )
//    @Mapping(source = "learning.id",target ="learningId")
//    @Mapping(source="booster", target = "booster")
    @Mapping(source = "proofType.id",target= "proofId")
    UserLearningResponseDTO toResponseDTO(UserLearning userLearning);

}
