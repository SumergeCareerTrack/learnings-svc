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
public class ScoreboardLevelResponseDTO {
    private UUID id;

    private String name;

    private int minScore;

}
