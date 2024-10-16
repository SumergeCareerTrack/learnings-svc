package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;

@CrossOrigin
@RestController
@RequestMapping("/learnings")
public class LearningController {
    @Autowired
    private LearningService learningService;

    ///////////// * POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningResponseDTO> createLearning(
            @RequestBody LearningRequestDTO learning) throws Exception {
        LearningResponseDTO newLearning = learningService.create(learning);
        return ResponseEntity.ok(newLearning);
    }

    ///////////// * GET METHODS */////////////
    @Tag(name = "Get Learnings")
    @GetMapping("/")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearnings(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            // Fetch all learnings without pagination
            List<LearningResponseDTO> learnings = learningService.getAll();
            return ResponseEntity.ok(learnings);
        } else {
            // Paginated fetch
            return ResponseEntity.ok(learningService.getAllPaginated(PageRequest.of(page, size)));
        }
    }

    @Tag(name = "Get Learnings")
    @GetMapping("/pending")
    public ResponseEntity<List<LearningResponseDTO>> getAllPendingLearnings(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            // Fetch all pending learnings without pagination
            List<LearningResponseDTO> learnings = learningService.getAllPending();
            return ResponseEntity.ok(learnings);
        } else {
            // Paginated fetch
            return ResponseEntity.ok(learningService.getAllPendingPaginated(PageRequest.of(page, size)));
        }
    }


    @Tag(name = "Get Learnings")
    @GetMapping("/approved")
    public ResponseEntity<List<LearningResponseDTO>> getAllNonPendingLearnings(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            // Fetch all approved learnings without pagination
            List<LearningResponseDTO> learnings = learningService.getAllNonPending();
            return ResponseEntity.ok(learnings);
        } else {
            // Paginated fetch
            return ResponseEntity.ok(learningService.getAllNonPendingPaginated(PageRequest.of(page, size)));
        }
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
            @RequestParam String type,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            // Fetch all learnings by type without pagination
            List<LearningResponseDTO> learnings = learningService.getLearningByType(type);
            return ResponseEntity.ok(learnings);
        } else {
            // Paginated fetch
            return ResponseEntity.ok(learningService.getLearningByTypePaginated(type, PageRequest.of(page, size)));
        }
    }


    @Tag(name = "Get Learnings")
    @GetMapping("/subject")
    public ResponseEntity<List<LearningResponseDTO>> getAllLearningsBySubject(
            @RequestParam String subject,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) throws Exception {

        if (page == null || size == null || size <= 0) {
            List<LearningResponseDTO> learnings = learningService.getAllLearningsBySubject(subject);
            return ResponseEntity.ok(learnings);
        } else {
            return ResponseEntity.ok(learningService.getAllLearningsBySubjectPaginated(subject, PageRequest.of(page, size)));
        }
    }

    ///////////// * UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/{learningId}")
    public ResponseEntity<?> updateLearning(
            @RequestBody LearningRequestDTO learning, @PathVariable UUID learningId) throws Exception {
        LearningResponseDTO updatedLearning = learningService.updateLearning(learningId, learning);
        return ResponseEntity.ok(updatedLearning);
    }

    ///////////// * DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteLearning(
            @PathParam("id ") UUID id) {
        learningService.deleteLearning(id);
        return ResponseEntity.ok().build();
    }

}
