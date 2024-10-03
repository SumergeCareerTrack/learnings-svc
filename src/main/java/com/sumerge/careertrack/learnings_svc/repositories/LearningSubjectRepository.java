package com.sumerge.careertrack.learnings_svc.repositories;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LearningSubjectRepository extends JpaRepository<LearningSubject, UUID> {

    boolean existsByTypeAndName(SubjectType type,String name);

    LearningSubject findByName(String subject);

    boolean existsByName(String subject);
}
