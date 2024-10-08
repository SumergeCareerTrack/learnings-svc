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

import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningSubjectService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/learnings/subjects")
@RequiredArgsConstructor
public class LearningSubjectController {

    private final LearningSubjectService learningSubjectService;

    ///////////// * POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningSubjectResponseDTO> createLearningSubject(
            @RequestBody LearningSubjectRequestDTO learning) throws Exception {
        LearningSubjectResponseDTO newLearning = learningSubjectService.createSubject(learning);
        return ResponseEntity.ok(newLearning);
    }

    ///////////// * GET METHODS */////////////
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

    ///////////// * UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/{SubjectId}")
    public ResponseEntity<LearningSubjectResponseDTO> updateSubject(
            @RequestBody LearningSubjectRequestDTO learning, @PathVariable UUID SubjectId) throws Exception {
        LearningSubjectResponseDTO updatedLearning = learningSubjectService.updateSubject(SubjectId, learning);
        return ResponseEntity.ok(updatedLearning);

    }

    ///////////// * DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteSubject(
            @PathParam("id ") UUID id) throws Exception {
        learningSubjectService.deleteSubject(id);
        return ResponseEntity.ok().build();

    }
}
