package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.ProofType;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.ProofTypeMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.ProofTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ProofTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProofTypeService {

    private final ProofTypesRepository proofTypesRepository;
    private final ProofTypeMapper proofTypeMapper;

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
        ProofType proofType = proofTypeMapper.toProofType(proofTypeRequestDTO);
        //TODO How do you handle nulls with exceptions **if handled add test**?
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
