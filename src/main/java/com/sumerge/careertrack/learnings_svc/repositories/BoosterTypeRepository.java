package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumerge.careertrack.learnings_svc.entities.BoosterType;

@Repository
public interface BoosterTypeRepository extends JpaRepository<BoosterType, UUID> {

    boolean existsByName(String typeName);

    Optional<BoosterType> findByName(String typeName);

}
