package com.sumerge.careertrack.learnings_svc.entities.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreResponseDTO {
    @lombok.NonNull
    private UUID userId;

    @lombok.NonNull
    private Integer score;
}
