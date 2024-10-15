package com.sumerge.careertrack.learnings_svc.services;



import com.sumerge.careertrack.learnings_svc.entities.requests.NotificationRequestDTO;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProducerService {
    @Value("${kafka.topic.name}")
    private String topicName;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationRequestDTO dto)  {
        JSONObject obj= new JSONObject(dto);
        kafkaTemplate.send(topicName, obj.toString());

    }



}