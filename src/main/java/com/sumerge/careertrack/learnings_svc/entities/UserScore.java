package com.sumerge.careertrack.learnings_svc.entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserScore implements Comparable<UserScore> {

    @Id
    private UUID userId;

    private Integer score;

    @Override
    public int compareTo(UserScore other) {
        Integer myScore = score;
        Integer otherScore = other.score;
        return Integer.compare(myScore, otherScore);
    }

}
