package com.sumerge.careertrack.learnings_svc.entities.requests;

import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private ApprovalStatus approvalStatus;

//    private UUID userId;

    private UUID learningId;

    private UUID boosterId;

    private UUID proofId;

}
