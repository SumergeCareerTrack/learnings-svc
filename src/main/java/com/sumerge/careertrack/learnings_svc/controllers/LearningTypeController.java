package com.sumerge.careertrack.learnings_svc.controllers;


import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/learnings/types")
@RequiredArgsConstructor
public class LearningTypeController {
    private final LearningTypeService learningTypeService;

    /////////////* POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")

    public ResponseEntity<LearningTypeResponseDTO> createLearningType(
            @RequestBody LearningTypeRequestDTO learning) throws Exception {
        LearningTypeResponseDTO newLearning = learningTypeService.createType(learning);
        return ResponseEntity.ok(newLearning);
    }

    /////////////* GET METHODS */////////////
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

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/")
    public ResponseEntity<LearningTypeResponseDTO> updateType(
            @RequestBody LearningTypeRequestDTO learningType) throws Exception {
        LearningTypeResponseDTO updatedLearning = learningTypeService.updateType(learningType.getId(),learningType);
        return ResponseEntity.ok(updatedLearning);
    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteSubject(
            @PathParam("id") UUID id) throws Exception {
        learningTypeService.deleteType(id);
        return ResponseEntity.ok().build();
    }


}
