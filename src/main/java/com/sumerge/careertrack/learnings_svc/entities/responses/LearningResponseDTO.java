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
public class LearningResponseDTO {
    @lombok.NonNull
    private UUID id;

    private String title;

    private String url;

    private String typeName;

    private int typeBaseScore;

    private String description;

    private String subjectType;

    private String subjectName;

    private float lengthInHours;
}
