package com.sumerge.careertrack.learnings_svc.services;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.NotificationRequestDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ProducerService {
    @Value("${kafka.topic.name}")
    private String topicName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationRequestDTO dto)  {
        JSONObject obj= new JSONObject(dto);
        System.out.println(obj.toString());
        kafkaTemplate.send("notification", obj.toString());

    }



}