package com.sumerge.careertrack.learnings_svc.entities.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserLearningRequestDTO {

    @lombok.NonNull
    private UUID type;

    @lombok.NonNull
    private UUID subject;

    private String title;

    private String url;

    private String description;

    private float lengthInHours;

    private String proof;

    private String comment;

    private Date date;

    @lombok.NonNull
    private UUID userId;

}
