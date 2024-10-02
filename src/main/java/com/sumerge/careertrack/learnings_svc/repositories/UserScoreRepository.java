package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumerge.careertrack.learnings_svc.entities.UserScore;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, UUID> {

}
