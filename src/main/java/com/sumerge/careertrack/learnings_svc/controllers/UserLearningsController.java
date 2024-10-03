package com.sumerge.careertrack.learnings_svc.controllers;

import com.sumerge.careertrack.learnings_svc.mappers.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.UserLearningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users-learnings")
public class UserLearningsController {

    @Autowired
    private UserLearningsService userLearningsService;

    @GetMapping
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUsersLearnings() {
        return ResponseEntity.ok(userLearningsService.getAllUserLearnings());
    }

    @GetMapping("/user/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> getUserLearningsById(@PathVariable UUID learningId) {
       return ResponseEntity.ok(userLearningsService.getUserLearningsByUserLearningId(learningId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUserLearningsByUserId(@PathVariable UUID userId) {
       return ResponseEntity.ok(userLearningsService.getUserLearningsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserLearningResponseDTO> createUserLearning(@RequestBody UserLearningRequestDTO userLearningRequestDTO) {
        return ResponseEntity.ok(userLearningsService.createUserLearning(userLearningRequestDTO));
    }

    @PutMapping("/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> updateUserLearnings(@PathVariable UUID learningId, @RequestBody UserLearningRequestDTO userLearningRequestDTO) {
        return ResponseEntity.ok(userLearningsService.updateUserLearning(learningId,userLearningRequestDTO));
    }

    @DeleteMapping("/{learningId}")
    public ResponseEntity<String> deleteUserLearning(@PathVariable UUID learningId) {
       return ResponseEntity.ok(userLearningsService.deleteUserLearning(learningId));
    }

    //TODO Should we add an auth token ?
    @PutMapping("/approve/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> approveUserLearning(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.approveLearning(learningId));
    }

    @PutMapping("/reject/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> rejectUserLearning(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.rejectLearning(learningId));
    }

    //TODO Add Learning if not exist****

}
