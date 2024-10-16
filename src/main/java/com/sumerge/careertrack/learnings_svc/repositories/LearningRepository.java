package com.sumerge.careertrack.learnings_svc.repositories;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface LearningRepository extends JpaRepository<Learning, UUID> {


    List<Learning> findByType(LearningType typeId);
    Page<Learning> findByType(LearningType typeId, Pageable page);



    List<Learning> findBySubject(LearningSubject learnSubject);
    Page<Learning> findBySubject(LearningSubject learnSubject, Pageable page);

    boolean existsByUrlAndDescriptionAndTypeAndSubject(String url, String description, LearningType type, LearningSubject subject);


    List<Learning> findByPending(boolean pending);
    Page<Learning> findByPending(boolean pending, Pageable page);

}
