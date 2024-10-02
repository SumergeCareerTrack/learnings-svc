package com.sumerge.careertrack.learnings_svc.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.entities.UserScore;
import com.sumerge.careertrack.learnings_svc.entities.UserScore.UserScoreBuilder;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserScoreResponseDTO;
import com.sumerge.careertrack.learnings_svc.mappers.UserScoreMapper;
import com.sumerge.careertrack.learnings_svc.repositories.UserScoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserScoreService {

    private UserScoreRepository repository;
    private UserScoreMapper mapper;

    private static UserScoreBuilder NULL_SCORE = UserScore.builder().score(0);

    public UserScoreResponseDTO getUserScore(UUID userId) {
        UserScore userScore = repository.findById(userId)
                .orElse(NULL_SCORE.userId(userId).build());

        return mapper.toDto(userScore);
    }

}
