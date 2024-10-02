package com.sumerge.careertrack.learnings_svc.controllers;


import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningSubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/learnings/subjects")
public class LearningSubjectController {

    @Autowired
    private LearningSubjectService learningSubjectService;

    /////////////* POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningSubjectResponseDTO> createLearningSubject(
            @RequestBody LearningSubjectRequestDTO learning) throws Exception {
        LearningSubjectResponseDTO newLearning = learningSubjectService.createSubject(learning);
        return ResponseEntity.ok(newLearning);
    }

    /////////////* GET METHODS */////////////
    @Tag(name = "Get")
    @GetMapping("/")
    public ResponseEntity<List<LearningSubjectResponseDTO>> getAllSubjects() {
        return null;
    }
    @Tag(name = "Get")
    @GetMapping("/{subjectId}")
    public ResponseEntity<?> getSubjectById(
            @PathVariable UUID subjectId) {
        return null;
    }

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/learning/")
    public ResponseEntity<?> updateSubject(
            @RequestBody LearningSubjectRequestDTO learning) {
        return null;
    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/learning/")
    public ResponseEntity<?> deleteSubject(
            @RequestBody LearningSubjectRequestDTO learning) {
        return null;
    }
}
