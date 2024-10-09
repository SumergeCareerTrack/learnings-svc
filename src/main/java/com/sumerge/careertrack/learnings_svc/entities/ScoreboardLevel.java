package com.sumerge.careertrack.learnings_svc.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreboardLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @lombok.NonNull
    @Column(nullable = false, unique = true)
    String name;

    @lombok.NonNull
    @Column(nullable = false, unique = true)
    Integer minScore;
}
