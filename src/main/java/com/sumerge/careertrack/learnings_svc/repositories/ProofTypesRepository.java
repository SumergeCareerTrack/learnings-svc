package com.sumerge.careertrack.learnings_svc.repositories;


import com.sumerge.careertrack.learnings_svc.entities.ProofType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProofTypesRepository extends JpaRepository<ProofType, UUID> {

}
