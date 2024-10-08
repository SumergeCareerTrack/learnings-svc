package com.sumerge.careertrack.learnings_svc.entities.responses;

import java.util.UUID;

import com.sumerge.careertrack.learnings_svc.entities.BoosterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoosterResponseDTO {

    @lombok.NonNull
    private UUID id;

    @lombok.NonNull
    private String name;

    @lombok.NonNull
    private BoosterType type;

    private boolean isActive;
}
