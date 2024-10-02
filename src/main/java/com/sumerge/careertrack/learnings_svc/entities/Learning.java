package com.sumerge.careertrack.learnings_svc.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Learning {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private LearningType type;

    private String url;

    private String description;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private LearningSubject subject;

    private float lengthInHours;

}
