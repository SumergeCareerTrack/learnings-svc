package com.sumerge.careertrack.learnings_svc.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    private UUID receiverID;
    private UUID actorId;
    private String actionName;
    private UUID entityId;
    private String entityTypeName;
    private Date date;
    private boolean seen;

}





