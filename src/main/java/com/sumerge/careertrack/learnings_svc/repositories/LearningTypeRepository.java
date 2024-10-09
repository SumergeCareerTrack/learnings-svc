package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumerge.careertrack.learnings_svc.entities.LearningType;

@Repository
public interface LearningTypeRepository extends JpaRepository<LearningType, UUID> {

    LearningType findByName(String typeName);

    boolean existsByName(String name);

    LearningType findByNameAndBaseScore(String name, int baseScore);
}
