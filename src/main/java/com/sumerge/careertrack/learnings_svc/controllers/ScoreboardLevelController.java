package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;

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

import com.sumerge.careertrack.learnings_svc.entities.ScoreboardLevel;
import com.sumerge.careertrack.learnings_svc.entities.requests.ScoreboardLevelRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ScoreboardLevelResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.ScoreboardService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/score/level")
@RequiredArgsConstructor
public class ScoreboardLevelController {

    private final ScoreboardService scoreboardService;

    @GetMapping("/")
    public List<ScoreboardLevel> getAll() {
        return scoreboardService.getAll();
    }

    @GetMapping("/{levelName}")
    public ScoreboardLevelResponseDTO getLevelByName(@PathVariable String levelName) {
        return scoreboardService.getByName(levelName);
    }

    @PostMapping("/")
    public ScoreboardLevelResponseDTO createLevel(
            @RequestBody ScoreboardLevelRequestDTO request) {
        return scoreboardService.create(request);
    }

    @PutMapping("/{levelName}")
    public ScoreboardLevelResponseDTO updateLevel(
            @PathVariable String levelName, @RequestBody ScoreboardLevelRequestDTO entity) {
        return scoreboardService.update(levelName, entity);
    }

    @DeleteMapping("/{levelName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLevel(@PathVariable String levelName) {
        scoreboardService.delete(levelName);
    }

}
