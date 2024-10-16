package com.sumerge.careertrack.learnings_svc.controllers;

import com.sumerge.careertrack.learnings_svc.entities.requests.CustomUserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.UserLearningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users-learnings")
@RequiredArgsConstructor
public class UserLearningsController {

    private final UserLearningsService userLearningsService;

    @GetMapping
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUsersLearnings(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            return ResponseEntity.ok(userLearningsService.getAllUserLearnings());
        } else {
            return ResponseEntity.ok(userLearningsService.getAllUserLearningsPaginated(PageRequest.of(page, size)));
        }
    }


    //specific user-learning
    @GetMapping("/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> getUserLearningsById(@PathVariable UUID learningId) {
       return ResponseEntity.ok(userLearningsService.getUserLearningByUserLearningId(learningId));
    }

    //TODO needs user -> tests**
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUserLearningsByUserId(@PathVariable UUID userId) {
       return ResponseEntity.ok(userLearningsService.getUserLearningsByUserId(userId));
    }

    //specific  user-learnings of learnings
    @GetMapping("/learning/{learningId}")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllUserLearningsByLearningId(@PathVariable UUID learningId) {
        return ResponseEntity.ok(userLearningsService.getAllUsersLearningsByLearningId(learningId));
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

    @PutMapping("/approve/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> approveUserLearning(
            @PathVariable UUID learningId,
            @RequestParam String comment,
            @RequestParam String managerId
    ) {
        return ResponseEntity.ok(userLearningsService.approveLearning(learningId,comment,managerId));
    }

    @PutMapping("/reject/{learningId}")
    public ResponseEntity<UserLearningResponseDTO> rejectUserLearning(
            @PathVariable UUID learningId,
            @RequestParam String comment,
            @RequestParam String managerId
            ) {
        return ResponseEntity.ok(userLearningsService.rejectLearning(learningId,managerId,comment));

    }

    @PostMapping("/custom-learning/{managerId}")
    public ResponseEntity<UserLearningResponseDTO> addCustomLearning(
            @RequestBody CustomUserLearningRequestDTO customUserLearning,
            @PathVariable String managerId
    ) throws Exception {
        return ResponseEntity.ok(userLearningsService.createCustomLearning(customUserLearning,managerId));
    }

    @PostMapping("/subordinates")
    public ResponseEntity<List<UserLearningResponseDTO>> getAllSubordinateUserLearnings(@RequestBody List<UUID> usersIds) {
        return ResponseEntity.ok(userLearningsService.getAllSubordinateUserLearnings(usersIds));
    }

}
