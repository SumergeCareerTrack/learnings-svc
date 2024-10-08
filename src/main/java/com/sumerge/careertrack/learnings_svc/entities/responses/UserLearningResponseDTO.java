package com.sumerge.careertrack.learnings_svc.entities.responses;

import com.sumerge.careertrack.learnings_svc.entities.Booster;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.ProofType;
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
public class UserLearningResponseDTO {

    private UUID id;

    private String proof;

    private String comment;

    private Date date;

    private ApprovalStatus approvalStatus;

    //TODO dependencies of foreign keys

    private UUID userId;
    private Learning learning;
    private Booster booster;

}
