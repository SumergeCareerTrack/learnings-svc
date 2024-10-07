package com.sumerge.careertrack.learnings_svc.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.services.LearningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {LearningController.class})
@AutoConfigureMockMvc(addFilters = false)
public class LearningControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LearningService learningService;

    private ObjectMapper objectMapper;

    Learning learning;
    LearningType learningType;
    LearningSubject learningSubject;
    LearningRequestDTO learningRequestDTO;
    LearningResponseDTO learningResponseDTO;

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.learningType = LearningType.builder().id(UUID.randomUUID()).name("Type Name").baseScore(10).build();
        this.learningSubject = LearningSubject.builder().id(UUID.randomUUID()).name("Subject Name").type(SubjectType.FUNCTIONAL).build();
        this.learning = Learning.builder().id(UUID.randomUUID()).type(this.learningType).url("www.TestUrl.com").description("Description").subject(this.learningSubject).build();
        this.learningRequestDTO = LearningRequestDTO.builder().type(this.learningType.getId()).url("www.TestUrl.com").description("Description").subject(this.learningSubject.getId()).build();
        this.learningResponseDTO = LearningResponseDTO.builder().id(this.learning.getId()).typeName(this.learningType.getName()).typeBaseScore(this.learningType.getBaseScore()).url(this.learning.getUrl()).description(this.learning.getDescription()).subjectName(this.learningSubject.getName()).subjectType(this.learningSubject.getType().toString()).build();
    }

    @Test
    public void createLearning_whenValidInput_thenReturns200() throws Exception {
        when(learningService.create(this.learningRequestDTO)).thenReturn(this.learningResponseDTO);
        ResultActions response = mockMvc.perform(post("/learnings/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningRequestDTO)));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(this.learningResponseDTO)));
    }
    @Test
    public void createLearning_whenTypeNotFound_ThrowsDoesNotExist() throws Exception {
        this.learningRequestDTO.setType(UUID.randomUUID());
        when(learningService.create(this.learningRequestDTO)).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, this.learningRequestDTO.getType()));
        ResultActions response = mockMvc.perform(post("/learnings/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningRequestDTO)));


        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE,this.learningRequestDTO.getType())));
    }
    @Test
    public void createLearning_whenSubjectNotFound_ThrowsDoesNotExist() throws Exception {
        this.learningRequestDTO.setSubject(UUID.randomUUID());
        when(learningService.create(this.learningRequestDTO)).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, this.learningRequestDTO.getSubject()));
        ResultActions response = mockMvc.perform(post("/learnings/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningRequestDTO)));


        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT,this.learningRequestDTO.getSubject())));
    }

    @Test
    public void getAllLearnings_fetchAll_ReturnsList() throws Exception {
        when(learningService.getAll()).thenReturn(List.of(this.learningResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(this.learningResponseDTO))));
    }
    @Test
    public void getAllLearnings_NoLearnings_ReturnEmptyList() throws Exception {
        when(learningService.getAll()).thenReturn(List.of());
        ResultActions response = mockMvc.perform(get("/learnings/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }
    @Test
    public void getLearningById_fetchLearning_ReturnLearning() throws Exception {
        when(learningService.getLearningById(this.learning.getId())).thenReturn((this.learningResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/{id}",
                this.learning.getId()));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString((this.learningResponseDTO))));
    }

    @Test
    public void getLearningById_NoLearning_ThrowsDoesNotExist() throws Exception {
        when(learningService.getLearningById(this.learning.getId())).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING, this.learning.getId()));
        ResultActions response = mockMvc.perform(get("/learnings/{id}",
                this.learning.getId()));

        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING,this.learning.getId())));
    }
    @Test
    public void getLearningsByType_fetchLearnings_ReturnListOfLearnings() throws Exception {
        when(learningService.getLearningByType(this.learningType.getName())).thenReturn(List.of(this.learningResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/type")
                .param("type", this.learningType.getName()));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(this.learningResponseDTO))));
    }
    @Test
    public void getLearningsByType_NoLearningsFound_ReturnEmptyList() throws Exception {
        when(learningService.getLearningByType(this.learningType.getName())).thenReturn(List.of());
        ResultActions response = mockMvc.perform(get("/learnings/type")
                .param("type", this.learningType.getName()));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }
    @Test
    public void getLearningsByType_TypeNotFound_ThrowsDoesNotExist() throws Exception {
        when(learningService.getLearningByType(this.learningType.getName())).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, this.learningType.getName()));
        ResultActions response = mockMvc.perform(get("/learnings/type")
                .param("type", this.learningType.getName()));

        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE,this.learningType.getName())));
    }

    @Test
    public void getAllLearningsBySubject_fetchLearnings_ReturnListOfLearnings() throws Exception {
        when(learningService.getAllLearningsBySubject(this.learningSubject.getName())).thenReturn(List.of(this.learningResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/subject")
                .param("subject", this.learningSubject.getName()));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(this.learningResponseDTO))));
    }
    @Test
    public void getAllLearningsBySubject_NoLearningsFound_ReturnEmptyList() throws Exception {
        when(learningService.getAllLearningsBySubject(this.learningSubject.getName())).thenReturn(List.of());
        ResultActions response = mockMvc.perform(get("/learnings/subject")
                .param("subject", this.learningSubject.getName()));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }
    @Test
    public void getAllLearningsBySubject_TypeNotFound_ThrowsDoesNotExist() throws Exception {
        when(learningService.getAllLearningsBySubject(this.learningSubject.getName())).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, this.learningSubject.getName()));
        ResultActions response = mockMvc.perform(get("/learnings/subject")
                .param("subject", this.learningSubject.getName()));

        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT,this.learningSubject.getName())));
    }

    @Test
    public void updateLearning_Success_ReturnsUpdatedLearning() throws Exception {
        learningResponseDTO.setDescription("NEWDesc");
        learningResponseDTO.setUrl("www.NEWURL.com");
        learningResponseDTO.setSubjectName("NEW SUB");
        learningResponseDTO.setSubjectType(SubjectType.ORGANISATIONAL.toString());
        learningResponseDTO.setTypeName("NEW TYPE");
        learningResponseDTO.setTypeBaseScore(2);
        when(learningService.updateLearning(this.learning.getId(),this.learningRequestDTO)).thenReturn(learningResponseDTO);

        mockMvc.perform(put("/learnings/{learningId}",this.learning.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(learningResponseDTO)));

    }

    @Test
    public void updateLearning_LearningNotFound_ThrowsDoesNotExistException() throws Exception {
        when(learningService.updateLearning(eq(this.learning.getId()), any(LearningRequestDTO.class)))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING, this.learning.getId()));

        mockMvc.perform(put("/learnings/{learningId}",this.learning.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING, learningResponseDTO.getId())));
    }

    @Test
    public void updateLearning_LearningTypeNotFound_ThrowsDoesNotExistException() throws Exception {
        when(learningService.updateLearning(eq(this.learning.getId()), any(LearningRequestDTO.class)))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, learningRequestDTO.getType()));

        mockMvc.perform(put("/learnings/{learningId}",this.learning.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE, learningRequestDTO.getType())));
    }

    @Test
    public void updateLearning_LearningSubjectNotFound_ThrowsDoesNotExistException() throws Exception {
        when(learningService.updateLearning(eq(this.learning.getId()), any(LearningRequestDTO.class)))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, learningRequestDTO.getSubject()));

        mockMvc.perform(put("/learnings/{learningId}",this.learning.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT, learningRequestDTO.getSubject())));
    }
    @Test
    public void deleteLearning_Success_ReturnsOk() throws Exception {
        doNothing().when(learningService).deleteLearning(this.learning.getId());

        mockMvc.perform(delete("/learnings/")
                        .param("id", String.valueOf(this.learningResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLearning_LearningNotFound_ThrowsDoesNotExistException() throws Exception {
        doThrow(new DoesNotExistException(DoesNotExistException.LEARNING,this.learning.getId()))
                .when(learningService).deleteLearning(this.learning.getId());

        mockMvc.perform(delete("/learnings/")
                        .param("id", String.valueOf(this.learningResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING, this.learning.getId())));
    }



}
