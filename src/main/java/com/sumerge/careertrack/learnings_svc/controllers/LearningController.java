package com.sumerge.careertrack.learnings_svc.controllers;

import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/learnings")
public class LearningController {
    @Autowired
    private LearningService learningService;


    /////////////* POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningResponseDTO> createLearning(
            @RequestBody LearningRequestDTO learning) throws Exception {
        LearningResponseDTO newLearning = learningService.create(learning);
        return ResponseEntity.ok(newLearning);
    }




    /////////////* GET METHODS */////////////
    @Tag(name = "Get Learnings")
    @GetMapping("/")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearnings() {
        List<LearningResponseDTO> learnings = learningService.getAll();
        return ResponseEntity.ok(learnings);
    }
    @Tag(name = "Get Learnings")
    @GetMapping("/{id}")
    public ResponseEntity<LearningResponseDTO> getLearningById(
            @PathVariable UUID id) throws Exception {
        LearningResponseDTO learning = learningService.getLearningById(id);
        return ResponseEntity.ok(learning);
    }
    @Tag(name = "Get Learnings")
    @GetMapping("/type")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearningsByType(
            @RequestParam String type) {
        List<LearningResponseDTO> learnings = learningService.getLearningByType(type);
        return ResponseEntity.ok(learnings);
    }
    @Tag(name = "Get Learnings")
    @GetMapping("/subject")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearningsBySubject(
            @RequestParam String subject) throws Exception {
        List<LearningResponseDTO> learnings = learningService.getAllLearningBySubject(subject);
        return ResponseEntity.ok(learnings);
    }

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/")
    public ResponseEntity<?> updateLearning(
            @RequestBody LearningRequestDTO learning) throws Exception {
        LearningResponseDTO updatedLearning = learningService.updateLearning(learning.getId(),learning);
        return ResponseEntity.ok(updatedLearning);
    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteLearning(
            @PathParam("id ") UUID id) {
        learningService.deleteLearning(id);
        return ResponseEntity.ok().build();
    }






}

