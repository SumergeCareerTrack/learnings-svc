package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.responses.UserScoreResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.UserScoreService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/score/user")
@RequiredArgsConstructor
public class UserScoresController {

    private UserScoreService service;

    @GetMapping("/{userId}")
    public UserScoreResponseDTO getUserScore(@PathVariable UUID userId) {
        return service.getUserScore(userId);
    }

}
