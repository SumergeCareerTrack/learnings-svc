package com.sumerge.careertrack.learnings_svc.mappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofTypeRequestDTO {
    private String name;
}
