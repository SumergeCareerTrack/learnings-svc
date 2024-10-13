package com.sumerge.careertrack.learnings_svc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sumerge.careertrack.learnings_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.learnings_svc.services.ProducerService;
import io.swagger.v3.core.util.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody NotificationRequestDTO dto) throws JsonProcessingException {
        producerService.sendMessage(dto);
        System.out.println("SENT: " + dto);
    }

}
