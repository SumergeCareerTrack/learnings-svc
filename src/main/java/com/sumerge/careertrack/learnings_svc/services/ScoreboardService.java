package com.sumerge.careertrack.learnings_svc.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.entities.ScoreboardLevel;
import com.sumerge.careertrack.learnings_svc.entities.requests.ScoreboardLevelRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ScoreboardLevelResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.ScoreboardLevelMapper;
import com.sumerge.careertrack.learnings_svc.repositories.ScoreboardLevelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreboardService {

    private final ScoreboardLevelRepository repository;
    private final ScoreboardLevelMapper mapper;

    public List<ScoreboardLevel> getAll() {
        return repository.findAll();
    }
    public List<ScoreboardLevel> getAllScoreboardLevelsPaginated(Pageable pageable) {
        Page<ScoreboardLevel> scoreboardLevelsPage = repository.findAll(pageable);
        return scoreboardLevelsPage.getContent();
    }


    public ScoreboardLevelResponseDTO getByName(String levelName) {
        ScoreboardLevel level = repository.findByName(levelName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.LEVEL, levelName));

        return mapper.toDto(level);
    }

    public ScoreboardLevelResponseDTO create(ScoreboardLevelRequestDTO request) {
        if (repository.existsByName(request.getName())) {
            throw new AlreadyExistsException(AlreadyExistsException.LEVEL_NAME, request.getName());
        }

        if (repository.existsByMinScore(request.getMinScore())) {
            throw new AlreadyExistsException(
                    AlreadyExistsException.LEVEL_SCORE, request.getMinScore());
        }

        ScoreboardLevel mappedRequest = mapper.toLevel(request);
        ScoreboardLevel newLevel = repository.save(mappedRequest);
        return mapper.toDto(newLevel);
    }

    public ScoreboardLevelResponseDTO update(String levelName, ScoreboardLevelRequestDTO request) {
        ScoreboardLevel level = repository.findByName(levelName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.LEVEL, levelName));

        level.setName(request.getName());
        level.setMinScore(request.getMinScore());
        repository.save(level);

        return mapper.toDto(level);
    }

    public void delete(String levelName) {
        ScoreboardLevel level = repository.findByName(levelName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.LEVEL, levelName));

        repository.delete(level);
    }

}
