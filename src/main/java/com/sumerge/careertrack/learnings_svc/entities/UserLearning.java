package com.sumerge.careertrack.learnings_svc.entities;

import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String proof;

    @Column(nullable = false)
    @Builder.Default
    private String comment ="";

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @Builder.Default
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    //TODO dependencies of foreign keys
    @Column(nullable = false)
    private UUID userId;

    @JoinColumn
    @ManyToOne
    private Learning learning;

    @JoinColumn
    @ManyToOne
    private Booster booster;

}
