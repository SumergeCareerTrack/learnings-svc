package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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

import com.sumerge.careertrack.learnings_svc.entities.UserScore;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserScoreResponseDTO;
import com.sumerge.careertrack.learnings_svc.mappers.UserScoreMapper;
import com.sumerge.careertrack.learnings_svc.services.UserScoreService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/score/user")
@RequiredArgsConstructor
public class UserScoresController {

    private final UserScoreService service;
    private final UserScoreMapper mapper;

    @GetMapping("/")
    public List<UserScoreResponseDTO> getAll() {
        List<UserScore> scores = service.getAll();
        return scores.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserScoreResponseDTO getUserScore(@PathVariable UUID userId) {
        UserScore score = service.getUserScore(userId);
        return mapper.toDto(score);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addUserScore(@PathVariable UUID userId, @RequestBody int score) {
        service.add(userId, score);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToUserScore(@PathVariable UUID userId, @RequestBody int amount) {
        service.addToUserScore(userId, amount);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserScore(@PathVariable UUID userId) {
        service.removeScore(userId);
    }

}
