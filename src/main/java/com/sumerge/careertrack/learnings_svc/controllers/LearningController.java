package com.sumerge.careertrack.learnings_svc.controllers;

import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "Get")
    @GetMapping("/")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearnings() {
        List<LearningResponseDTO> learnings = learningService.getAll();
        return ResponseEntity.ok(learnings);
    }
    @Tag(name = "Get")
    @GetMapping("/{id}")
    public ResponseEntity<LearningResponseDTO> getLearningById(
            @PathVariable UUID id) {
        LearningResponseDTO learning = learningService.getLearningById(id);
        return ResponseEntity.ok(learning);
    }
    @Tag(name = "Get")
    @GetMapping("/type/")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearningsByType(
            @RequestParam String type) {
        List<LearningResponseDTO> learnings = learningService.getLearningByType(type);
        return ResponseEntity.ok(learnings);
    }
    @Tag(name = "Get")
    @GetMapping("/subject/")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearningsBySubject(
            @RequestParam String subject) {
    return null;
    }

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/learning/")
    public ResponseEntity<?> updateLearning(
            @RequestBody LearningRequestDTO learning) {
        return null;
    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/learning/")
    public void deleteLearning(
            @RequestBody LearningRequestDTO learning) {
    }






}

