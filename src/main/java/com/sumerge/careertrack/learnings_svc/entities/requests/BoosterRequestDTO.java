package com.sumerge.careertrack.learnings_svc.entities.requests;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
BoosterRequestDTO {

    @lombok.NonNull
    private String name;

    @lombok.NonNull
    private UUID type;

    private boolean isActive;
}
