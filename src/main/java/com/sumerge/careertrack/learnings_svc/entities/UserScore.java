package com.sumerge.careertrack.learnings_svc.entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserScore {

    @Id
    private UUID userId;

    private Integer score;

}
