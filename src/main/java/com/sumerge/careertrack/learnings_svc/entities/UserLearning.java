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
    private String comment;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private ApprovalStatus approvalStatus;

    //TODO dependencies of foreign keys
//    @JoinColumn
//    @ManyToOne
//    private User user;
//
//    @JoinColumn
//    @ManyToOne
//    private Learning learning
//
//    @JoinColumn
//    @ManyToOne
//    private Booster booster

    @JoinColumn
    @ManyToOne
    private ProofType proofType;

}
