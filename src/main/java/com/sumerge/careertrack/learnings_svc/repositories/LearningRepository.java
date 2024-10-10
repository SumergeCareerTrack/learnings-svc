package com.sumerge.careertrack.learnings_svc.repositories;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface LearningRepository extends JpaRepository<Learning, UUID> {


    List<Learning> findByType(LearningType typeId);

    boolean existsByUrlAndDescription(String url,String description);

    List<Learning> findByUrlAndDescription(String url, String description);

    List<Learning> findBySubject(LearningSubject learnSubject);

    boolean existsByUrlAndDescriptionAndTypeAndSubject(String url, String description, LearningType type, LearningSubject subject);

    List<Learning> findByUrlAndDescriptionAndTypeAndSubject(String url, String description, LearningType type, LearningSubject subject);

    List<Learning> findByApproved(boolean approved);
}
