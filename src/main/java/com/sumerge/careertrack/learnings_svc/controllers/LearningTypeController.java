package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.requests.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/learnings/types")
@RequiredArgsConstructor
public class LearningTypeController {
    private final LearningTypeService learningTypeService;

    ///////////// * POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")

    public ResponseEntity<LearningTypeResponseDTO> createLearningType(
            @RequestBody LearningTypeRequestDTO learning) throws Exception {
        LearningTypeResponseDTO newLearning = learningTypeService.createType(learning);
        return ResponseEntity.ok(newLearning);
    }

    ///////////// * GET METHODS */////////////
    @Tag(name = "Get Types")
    @GetMapping("/")
    public ResponseEntity<List<LearningTypeResponseDTO>> getAllType() {
        List<LearningTypeResponseDTO> learnings = learningTypeService.getAll();
        return ResponseEntity.ok(learnings);
    }

    @Tag(name = "Get Types")
    @GetMapping("/{TypeId}")
    public ResponseEntity<LearningTypeResponseDTO> getSubjectById(
            @PathVariable UUID TypeId) throws Exception {
        LearningTypeResponseDTO learning = learningTypeService.getById(TypeId);
        return ResponseEntity.ok(learning);
    }

    ///////////// * UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/{typeId}")
    public ResponseEntity<LearningTypeResponseDTO> updateType(
            @RequestBody LearningTypeRequestDTO learningType, @PathVariable UUID typeId) throws Exception {
        LearningTypeResponseDTO updatedLearning = learningTypeService.updateType(typeId, learningType);
        return ResponseEntity.ok(updatedLearning);
    }

    ///////////// * DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteSubject(
            @PathParam("id") UUID id) throws Exception {
        learningTypeService.deleteType(id);
        return ResponseEntity.ok().build();
    }

}
