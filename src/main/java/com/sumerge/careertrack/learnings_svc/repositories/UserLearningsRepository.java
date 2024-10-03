package com.sumerge.careertrack.learnings_svc.repositories;

import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserLearningsRepository extends JpaRepository<UserLearning, UUID> {

    List<UserLearning> findAllById(UUID userId);
}
