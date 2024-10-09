package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.sumerge.careertrack.learnings_svc.entities.UserScore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserScoreRepository {
    public static final String LEADERBOARD_KEY = "LEADERBOARD";
    private final RedisTemplate<Object, Object> userScoresTemplate;

    public List<Object> getTopPlayers(int topN) {
        Set<Object> topPlayersSet = userScoresTemplate.opsForZSet()
                .reverseRange(LEADERBOARD_KEY, 0, topN - 1);
        return topPlayersSet.stream().collect(Collectors.toList());
    }

    public UserScore findById(UUID playerId) {
        Double score = userScoresTemplate.opsForZSet().score(LEADERBOARD_KEY, playerId.toString());

        if (score == null) {
            return UserScore.builder()
                    .userId(playerId)
                    .score(0)
                    .build();
        }

        return UserScore.builder()
                .userId(playerId)
                .score(score.intValue())
                .build();
    }

    public boolean existsById(UUID playerId) {
        Double score = userScoresTemplate.opsForZSet().score(LEADERBOARD_KEY, playerId.toString());
        return score != null;
    }

    public List<UserScore> findAll() {
        Set<TypedTuple<Object>> scoresSet = userScoresTemplate.opsForZSet()
                .unionWithScores(LEADERBOARD_KEY, Collections.emptyList());

        return scoresSet.stream()
                .map(score -> new UserScore(
                        UUID.fromString(score.getValue().toString()),
                        score.getScore().intValue()))
                .collect(Collectors.toList());
    }

    public void save(UUID playerId, int score) {
        userScoresTemplate.opsForZSet().addIfAbsent(LEADERBOARD_KEY, playerId.toString(), score);
    }

    public void addToUserScore(UUID playerId, int delta) {
        userScoresTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, playerId.toString(), delta);
    }

    public void removeScore(UUID playerId) {
        userScoresTemplate.opsForZSet().remove(LEADERBOARD_KEY, playerId.toString());
    }
}
