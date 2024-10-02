package com.sumerge.careertrack.learnings_svc.repositories;

import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import jakarta.persistence.metamodel.IdentifiableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LearningTypeRepository extends JpaRepository<LearningType, UUID> {

    LearningType findByName(String typeName);

    boolean existsByName(String name);
}
