package com.sumerge.careertrack.learnings_svc.repositories;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserTokenRepository {

    private final String hashKey = "TOKENS";
    private final RedisTemplate<String, String> userTokensTemplate;

    public boolean existsById(UUID userId) {
        Object x = userTokensTemplate.opsForHash().get(hashKey, userId.toString());
        return x != null;
    }

}
