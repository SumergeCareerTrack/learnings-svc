package com.sumerge.careertrack.learnings_svc.entities.requests;

import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLearningRequestDTO {

    private String proof;

    private String comment;

    private Date date;

    @NonNull
    private UUID userId;

    private UUID learningId;
}
