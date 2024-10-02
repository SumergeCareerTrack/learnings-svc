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

    private String description;

    private String subject;

    private float lengthInHours;
}
