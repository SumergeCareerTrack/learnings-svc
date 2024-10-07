package com.sumerge.careertrack.learnings_svc.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProofTypeResponseDTO {

    private UUID id;
    private String name;
}
