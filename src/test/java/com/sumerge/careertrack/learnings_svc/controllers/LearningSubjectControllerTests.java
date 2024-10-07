package com.sumerge.careertrack.learnings_svc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;

import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.services.LearningSubjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {LearningSubjectController.class})
@AutoConfigureMockMvc(addFilters = false)
public class LearningSubjectControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LearningSubjectService learningSubjectService;
    private ObjectMapper objectMapper;

    Learning learning;
    LearningType learningType;
    LearningSubject learningSubject;
    LearningSubjectRequestDTO learningSubjectRequestDTO;
    LearningSubjectResponseDTO learningSubjectResponseDTO;

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.learningType = LearningType.builder().id(UUID.randomUUID()).name("Type Name").baseScore(10).build();
        this.learningSubject = LearningSubject.builder().id(UUID.randomUUID()).name("Subject Name").type(SubjectType.FUNCTIONAL).build();
        this.learning = Learning.builder().id(UUID.randomUUID()).type(this.learningType).url("www.TestUrl.com").description("Description").subject(this.learningSubject).build();
        this.learningSubjectRequestDTO = LearningSubjectRequestDTO.builder().name(this.learningType.getName()).type(this.learningType.getName()).build();
        this.learningSubjectResponseDTO = LearningSubjectResponseDTO.builder().id(this.learningType.getId()).name(this.learningType.getName()).type(this.learningType.getName()).build();
    }


    @Test
    public void createLearningSubject_whenValidInput_thenReturns200() throws Exception {
        when(learningSubjectService.createSubject(this.learningSubjectRequestDTO)).thenReturn(this.learningSubjectResponseDTO);
        ResultActions response = mockMvc.perform(post("/learnings/subjects/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningSubjectRequestDTO)));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(this.learningSubjectResponseDTO)));
    }
    @Test
    public void createLearningSubject_SubjectExists_ThrowsAlreadyExists() throws Exception {
        when(learningSubjectService.createSubject(this.learningSubjectRequestDTO)).thenThrow(new AlreadyExistsException(AlreadyExistsException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName()));
        ResultActions response = mockMvc.perform(post("/learnings/subjects/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningSubjectResponseDTO)));


        response.andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyExistsException))
                .andExpect(content().string(String.format(AlreadyExistsException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName())));

    }
    @Test
    public void getAllSubjects_FetchAll_thenReturns200() throws Exception {
        when(learningSubjectService.getAllSubjects()).thenReturn(List.of(this.learningSubjectResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/subjects/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(this.learningSubjectResponseDTO))));
    }
    @Test
    public void getAllSubjects_NothingFound_ReturnEmptyList() throws Exception {
        when(learningSubjectService.getAllSubjects()).thenReturn(List.of());
        ResultActions response = mockMvc.perform(get("/learnings/subjects/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    public void getSubjectById_FetchType_thenReturns200() throws Exception {
        when(learningSubjectService.getSubjectById(this.learningSubject.getId())).thenReturn(this.learningSubjectResponseDTO);
        ResultActions response = mockMvc.perform(get("/learnings/subjects/{subjectId}", this.learningSubject.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(this.learningSubjectResponseDTO)));
    }
    @Test
    public void getSubjectById_DoesntExist_ThrowDoesntExist() throws Exception {
        when(learningSubjectService.getSubjectById(this.learningSubject.getId())).thenThrow(
                new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName())
        );
        ResultActions response = mockMvc.perform(get("/learnings/subjects/{subjectId}", this.learningSubject.getId()));


        response.andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName())));
    }
    @Test
    public void updateSubject_whenValidInput_thenReturns200() throws Exception {
        learningSubjectResponseDTO.setName("New Name");
        learningSubjectResponseDTO.setType(SubjectType.FUNCTIONAL.toString());
        when(learningSubjectService.updateSubject(this.learningSubject.getId(), this.learningSubjectRequestDTO)).thenReturn(this.learningSubjectResponseDTO);
        mockMvc.perform(put("/learnings/subjects/{id}", this.learningSubject.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningSubjectRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(learningSubjectResponseDTO)));
    }
    @Test
    public void updateSubject_SubjectNotFound_ThrowsDoesNotExist() throws Exception {
        when(learningSubjectService.updateSubject(this.learningSubject.getId(), this.learningSubjectRequestDTO))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName()));
        mockMvc.perform(put("/learnings/subjects/{id}", this.learningSubject.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningSubjectRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName())));
    }
    @Test
    public void deleteSubject_Success_ReturnsOk() throws Exception {
        doNothing().when(learningSubjectService).deleteSubject(this.learningSubject.getId());

        mockMvc.perform(delete("/learnings/subjects/")
                        .param("id", String.valueOf(this.learningSubjectResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteSubject_SubjectDoesntExist_ThrowsDoesntExist() throws Exception {
        doThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT,this.learningSubjectRequestDTO.getName()))
                .when(learningSubjectService).deleteSubject(this.learningSubject.getId());

        mockMvc.perform(delete("/learnings/subjects/")
                        .param("id", this.learningSubject.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_SUBJECT, this.learningSubjectRequestDTO.getName())));
    }
    @Test
    public void deleteSubject_LearningUsesSubject_ThrowsAlreadyExists() throws Exception {
        doThrow(new AlreadyExistsException(AlreadyExistsException.LEARNING_HAS_SUBJECT,this.learningSubjectRequestDTO.getName()))
                .when(learningSubjectService).deleteSubject(this.learningSubject.getId());

        mockMvc.perform(delete("/learnings/subjects/")
                        .param("id", String.valueOf(this.learningSubject.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyExistsException))
                .andExpect(content().string(String.format(AlreadyExistsException.LEARNING_HAS_SUBJECT, this.learningSubjectRequestDTO.getName())));
    }


}
