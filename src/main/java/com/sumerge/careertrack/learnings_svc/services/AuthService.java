package com.sumerge.careertrack.learnings_svc.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.repositories.UserTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserTokenRepository userTokenRepository;

    public boolean isValidUserId(UUID userId) {
        return userTokenRepository.existsById(userId);
    }
}
