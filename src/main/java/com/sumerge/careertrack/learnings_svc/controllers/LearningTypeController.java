package com.sumerge.careertrack.learnings_svc.controllers;


import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.LearningTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/learnings/types")
public class LearningTypeController {
    @Autowired
    private LearningTypeService learningTypeService;

    /////////////* POSt METHODS */////////////
    @Tag(name = "Post")
    @PostMapping("/")
    public ResponseEntity<LearningTypeResponseDTO> createLearningType(
            @RequestBody LearningTypeRequestDTO learning) throws Exception {
        LearningTypeResponseDTO newLearning = learningTypeService.createType(learning);
        return ResponseEntity.ok(newLearning);
    }

    /////////////* GET METHODS */////////////
    @Tag(name = "Get")
    @GetMapping("/")
    public ResponseEntity<List<LearningTypeResponseDTO>> getAllType() {
        return null;
    }
    @Tag(name = "Get")
    @GetMapping("/{TypeId}")
    public ResponseEntity<?> getSubjectById(
            @PathVariable UUID TypeId) {
        return null;
    }

    /////////////* UPDATE METHODS */////////////

    @Tag(name = "Update")
    @PutMapping("/learning/")
    public ResponseEntity<?> updateType(
            @RequestBody LearningTypeRequestDTO learningType) {
        return null;
    }

    /////////////* DELETE METHODS */////////////

    @Tag(name = "Delete")
    @DeleteMapping("/learning/")
    public ResponseEntity<?> deleteSubject(
            @RequestBody LearningTypeRequestDTO learning) {
        return null;
    }


}
