package com.sumerge.careertrack.learnings_svc.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningRequestDTO {

    @lombok.NonNull
    private UUID type;

    @lombok.NonNull
    private UUID subject;

    private String title;

    private String url;

    private String description;

    private float lengthInHours;

}
