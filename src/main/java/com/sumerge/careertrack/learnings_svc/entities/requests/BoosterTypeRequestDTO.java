package com.sumerge.careertrack.learnings_svc.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoosterTypeRequestDTO {
    @lombok.NonNull
    private String name;

    @lombok.NonNull
    private Integer value;
}
