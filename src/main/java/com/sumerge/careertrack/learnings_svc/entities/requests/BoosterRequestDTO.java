package com.sumerge.careertrack.learnings_svc.entities.requests;

import com.sumerge.careertrack.learnings_svc.entities.BoosterType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoosterRequestDTO {

    @lombok.NonNull
    private String name;

    @lombok.NonNull
    private BoosterType type;

    private boolean isActive;
}
