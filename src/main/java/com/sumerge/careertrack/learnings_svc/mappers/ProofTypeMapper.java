package com.sumerge.careertrack.learnings_svc.mappers;

import com.sumerge.careertrack.learnings_svc.entities.ProofType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProofTypeMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(source = "name" , target = "name")
   ProofType toProofType(ProofTypeRequestDTO requestDTO);

    @Mapping(source = "id" , target = "id")
    @Mapping(source = "name" , target = "name")
    ProofTypeResponseDTO  toResponseDTO(ProofType proofType);
}
