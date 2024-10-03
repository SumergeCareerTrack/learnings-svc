package com.sumerge.careertrack.learnings_svc.entities.responses;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningResponseDTO {
    @lombok.NonNull
    private UUID id;

    private String url;

    private String typeName;
    private int typeBaseScore;

    private String description;

    private String subjectType;
    private String subjectName;

    private float lengthInHours;
}
