package com.sumerge.careertrack.learnings_svc.entities.requests;

import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningSubjectRequestDTO {

    private UUID id;

    private String type;

    private String name;

}
