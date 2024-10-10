package com.sumerge.careertrack.learnings_svc.repositories;

import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserLearningsRepository extends JpaRepository<UserLearning, UUID> {
    List<UserLearning> findAllById(UUID userLearningId);
    List<UserLearning> findAllByLearning_Id(UUID learningId);
    List<UserLearning> findAllByUserId(UUID userId);
}
