package com.sumerge.careertrack.learnings_svc.controllers;

import com.sumerge.careertrack.learnings_svc.entities.requests.ProofTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ProofTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.ProofTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/proofs")
@RequiredArgsConstructor
public class ProofTypeController {

    private final ProofTypeService proofTypeService;

    @GetMapping
    public ResponseEntity<List<ProofTypeResponseDTO>> getAllProofTypes() {
        return ResponseEntity.ok(proofTypeService.getAllProofTypes());
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<ProofTypeResponseDTO> getProofTypeById(@PathVariable UUID typeId) {
        return ResponseEntity.ok(proofTypeService.getProofTypeById(typeId));
    }

    @PostMapping
    public ResponseEntity<ProofTypeResponseDTO> createProofType(@RequestBody ProofTypeRequestDTO proofTypeRequestDTO) {
        return ResponseEntity.ok(proofTypeService.createProofType(proofTypeRequestDTO));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<ProofTypeResponseDTO> updateProofType(@PathVariable UUID typeId, @RequestBody ProofTypeRequestDTO proofTypeRequestDTO) {
        return ResponseEntity.ok(proofTypeService.updateProofType(typeId, proofTypeRequestDTO));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<String> deleteProofTypeById(@PathVariable UUID typeId) {
        return ResponseEntity.ok(proofTypeService.deleteProofById(typeId));
    }

}
