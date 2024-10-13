package com.sumerge.careertrack.learnings_svc.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.NotificationRequestDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

@Service
public class ProducerService {
    @Value("${kafka.topic.name}")
    private String topicName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message)  {
        System.out.println("Sending message: " + message);
        kafkaTemplate.send("notification", message);

    }



}