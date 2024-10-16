package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ScoreboardLevel>> getAllScoreboardLevels(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null || size <= 0) {
            // Fetch all scoreboard levels without pagination
            return ResponseEntity.ok(scoreboardService.getAll());
        } else {
            // Paginated fetch
            return ResponseEntity.ok(scoreboardService.getAllScoreboardLevelsPaginated(PageRequest.of(page, size)));
        }
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
