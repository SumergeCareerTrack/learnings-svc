package com.sumerge.careertrack.learnings_svc.controllers;


import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningSubjectService;
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
@RequestMapping("/learnings/subjects")
@RequiredArgsConstructor
public class LearningSubjectController {

    private final LearningSubjectService learningSubjectService;

    /////////////* POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningSubjectResponseDTO> createLearningSubject(
            @RequestBody LearningSubjectRequestDTO learning) throws Exception {
        LearningSubjectResponseDTO newLearning = learningSubjectService.createSubject(learning);
        return ResponseEntity.ok(newLearning);
    }

    /////////////* GET METHODS */////////////
    @Tag(name = "Get Subjects")
    @GetMapping("/")
    public ResponseEntity<List<LearningSubjectResponseDTO>> getAllSubjects() {
        List<LearningSubjectResponseDTO> subjects = learningSubjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    @Tag(name = "Get Subjects")
    @GetMapping("/{subjectId}")
    public ResponseEntity<LearningSubjectResponseDTO> getSubjectById(
            @PathVariable UUID subjectId) throws Exception {
        LearningSubjectResponseDTO subject = learningSubjectService.getSubjectById(subjectId);
        return ResponseEntity.ok(subject);
    }

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/")
    public ResponseEntity<LearningSubjectResponseDTO> updateSubject(
            @RequestBody LearningSubjectRequestDTO learning) throws Exception {
        LearningSubjectResponseDTO updatedLearning = learningSubjectService.updateSubject(learning.getId(),learning);
        return ResponseEntity.ok(updatedLearning);

    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteSubject(
            @PathParam("id ")  UUID id) throws Exception {
        learningSubjectService.deleteSubject(id);
        return ResponseEntity.ok().build();

    }
}
