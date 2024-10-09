package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.BoosterService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/booster")
@RequiredArgsConstructor
public class BoosterController {

    private final BoosterService service;

    @GetMapping
    public ResponseEntity<List<BoosterResponseDTO>> getAll() {
        List<BoosterResponseDTO> boosters = service.getAllBoosters();
        return ResponseEntity.ok(boosters);
    }

    @GetMapping("/{boosterId}")
    public ResponseEntity<BoosterResponseDTO> getById(UUID boosterId) {
        BoosterResponseDTO booster = service.getById(boosterId);
        return ResponseEntity.ok(booster);
    }

    @PostMapping
    public ResponseEntity<BoosterResponseDTO> create(
            @RequestBody BoosterRequestDTO request) {
        BoosterResponseDTO newBooster = service.create(request);
        return ResponseEntity.ok(newBooster);
    }

    @PutMapping("/{boosterName}")
    public ResponseEntity<BoosterResponseDTO> update(@PathVariable String boosterName,
            @RequestBody BoosterRequestDTO request) {
        BoosterResponseDTO newType = service.update(boosterName, request);
        return ResponseEntity.ok(newType);
    }

    @DeleteMapping("/{boosterName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLevel(@PathVariable String boosterName) {
        service.delete(boosterName);
    }

}
