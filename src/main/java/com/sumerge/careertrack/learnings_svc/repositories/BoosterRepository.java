package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumerge.careertrack.learnings_svc.entities.Booster;

@Repository
public interface BoosterRepository extends JpaRepository<Booster, UUID> {

    boolean existsByName(String typeName);
    Optional<Booster> findFirstByIsActiveTrue();
    Optional<Booster> findByName(String typeName);

}
