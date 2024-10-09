package com.sumerge.careertrack.learnings_svc.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.entities.UserScore;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.repositories.UserScoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserScoreService {

    public static final String LEADERBOARD_KEY = "LEADERBOARD";
    private final UserScoreRepository repository;

    public List<Object> getTopPlayers(int topN) {
        return repository.getTopPlayers(topN);
    }

    public UserScore getUserScore(UUID userId) {
        return repository.findById(userId);
    }

    public List<UserScore> getAll() {
        return repository.findAll();
    }

    public void add(UUID playerId, int score) {
        if (repository.existsById(playerId)) {
            throw new AlreadyExistsException(AlreadyExistsException.SCORE, playerId);
        }

        repository.save(playerId, score);
    }

    public void addToUserScore(UUID playerId, int delta) {
        repository.addToUserScore(playerId, delta);
    }

    public void removeScore(UUID playerId) {
        repository.removeScore(playerId);
    }
}
