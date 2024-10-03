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

@Entity
@Data
@Builder
@AllArgsConstructor
public class BoosterType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @lombok.NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @lombok.NonNull
    @Column(nullable = false)
    private Integer value;
}
