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
public class BoosterTypeResponseDTO {
    @lombok.NonNull
    private UUID id;

    @lombok.NonNull
    private String name;

    @lombok.NonNull
    private Integer value;
}
