package com.sumerge.careertrack.learnings_svc.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class UserScore {

    @Id
    private UUID userId;

    @Column(nullable = false)
    private int score;

}
