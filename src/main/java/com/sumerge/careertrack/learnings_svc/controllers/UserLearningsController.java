package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.UserLearningsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users-learnings")
@RequiredArgsConstructor
public class UserLearningsController {

    private final UserLearningsService userLearningsService;

    @GetMapping
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUsersLearnings() {
        return ResponseEntity.ok(userLearningsService.getAllUserLearnings());
    }

    // specific user-learning
    @GetMapping("/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> getUserLearningsById(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.getUserLearningByUserLearningId(learningId));
    }

    // TODO needs user -> tests**
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUserLearningsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(userLearningsService.getUserLearningsByUserId(userId));
    }

    // specific user-learnings of learnings
    @GetMapping("/learning/{learningId}")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUserLearningsByLearningId(
            @PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.getAllUsersLearningsByLearningId(learningId));
    }

    @PostMapping
    public ResponseEntity<UserLearningResponseDTO> createUserLearning(
            @RequestBody UserLearningRequestDTO userLearningRequestDTO) {
        return ResponseEntity.ok(userLearningsService.createUserLearning(userLearningRequestDTO));
    }

    @PutMapping("/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> updateUserLearnings(@PathVariable UUID learningId,
            @RequestBody UserLearningRequestDTO userLearningRequestDTO) {
        return ResponseEntity.ok(userLearningsService.updateUserLearning(learningId, userLearningRequestDTO));
    }

    @DeleteMapping("/{learningId}")
    public ResponseEntity<String> deleteUserLearning(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.deleteUserLearning(learningId));
    }

    // TODO Should we add an auth token ?
    @PutMapping("/approve/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> approveUserLearning(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.approveLearning(learningId));
    }

    @PutMapping("/reject/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> rejectUserLearning(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.rejectLearning(learningId));
    }

    // TODO Add Learning if not exist****

}
