package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.ProofType;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.ProofTypeMapper;
import com.sumerge.careertrack.learnings_svc.mappers.ProofTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.mappers.ProofTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProofTypeService {

    @Autowired
    ProofTypesRepository proofTypesRepository;

    @Autowired
    ProofTypeMapper proofTypeMapper;

    public List<ProofTypeResponseDTO> getAllProofTypes() {
        List<ProofType> userLearnings = proofTypesRepository.findAll();
        return userLearnings.stream().map(proofTypeMapper::toResponseDTO).collect(Collectors.toList());
    }

    public ProofTypeResponseDTO getProofTypeById(UUID typeId) {
        ProofType proofType = proofTypesRepository.findById(typeId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.PROOF_TYPE,typeId));
        return proofTypeMapper.toResponseDTO(proofType);
    }

    public ProofTypeResponseDTO createProofType(ProofTypeRequestDTO proofTypeRequestDTO) {
        System.out.println(proofTypeRequestDTO);
        ProofType proofType = proofTypeMapper.toProofType(proofTypeRequestDTO);
        //TODO How do you handle nulls with exceptions?
        return proofTypeMapper.toResponseDTO(proofTypesRepository.save(proofType));
    }

    public ProofTypeResponseDTO updateProofType(UUID typeId, ProofTypeRequestDTO proofTypeRequestDTO) {
        if(!proofTypesRepository.existsById(typeId)) {
            throw new DoesNotExistException(DoesNotExistException.PROOF_TYPE,typeId);
        }
        return proofTypeMapper.toResponseDTO(proofTypesRepository
                .save(proofTypeMapper.toProofType(proofTypeRequestDTO)));
    }

    public String deleteProofById(UUID typeId) {
        ProofType proofType = proofTypesRepository.findById(typeId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.PROOF_TYPE,typeId));

        proofTypesRepository.delete(proofType);
        return "Deleted Proof Successfully";
    }







}
