package com.sumerge.careertrack.learnings_svc.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.services.LearningTypeService;
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
@WebMvcTest(controllers = {LearningTypeController.class})
@AutoConfigureMockMvc(addFilters = false)
public class LearningTypeControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private LearningTypeService learningTypeService;


    private ObjectMapper objectMapper;

    Learning learning;
    LearningType learningType;
    LearningSubject learningSubject;
    LearningTypeRequestDTO learningTypeRequestDTO;
    LearningTypeResponseDTO learningTypeResponseDTO;

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.learningType = LearningType.builder().id(UUID.randomUUID()).name("Type Name").baseScore(10).build();
        this.learningSubject = LearningSubject.builder().id(UUID.randomUUID()).name("Subject Name").type(SubjectType.FUNCTIONAL).build();
        this.learning = Learning.builder().id(UUID.randomUUID()).type(this.learningType).url("www.TestUrl.com").description("Description").subject(this.learningSubject).build();
        this.learningTypeRequestDTO = LearningTypeRequestDTO.builder().name(this.learningType.getName()).baseScore(this.learningType.getBaseScore()).build();
        this.learningTypeResponseDTO = LearningTypeResponseDTO.builder().id(this.learningType.getId()).name(this.learningType.getName()).baseScore(this.learningType.getBaseScore()).build();
    }


    @Test
    public void createLearningType_whenValidInput_thenReturns200() throws Exception {
        when(learningTypeService.createType(this.learningTypeRequestDTO)).thenReturn(this.learningTypeResponseDTO);
        ResultActions response = mockMvc.perform(post("/learnings/types/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningTypeRequestDTO)));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(this.learningTypeResponseDTO)));
    }
    @Test
    public void createLearningType_TypeExists_ThrowsAlreadyExists() throws Exception {
        when(learningTypeService.createType(this.learningTypeRequestDTO)).thenThrow(new AlreadyExistsException(AlreadyExistsException.LEARNING_TYPE, this.learningTypeRequestDTO.getName()));
        ResultActions response = mockMvc.perform(post("/learnings/types/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningTypeRequestDTO)));


        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyExistsException))
                .andExpect(content().string(String.format(AlreadyExistsException.LEARNING_TYPE, this.learningTypeRequestDTO.getName())));

    }
    @Test
    public void getAllType_FetchAll_thenReturns200() throws Exception {
        when(learningTypeService.getAll()).thenReturn(List.of(this.learningTypeResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/types/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(this.learningTypeResponseDTO))));
    }
    @Test
    public void getAllType_NothingFound_ReturnEmptyList() throws Exception {
        when(learningTypeService.getAll()).thenReturn(List.of());
        ResultActions response = mockMvc.perform(get("/learnings/types/"));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }
    @Test
    public void getById_FetchType_thenReturns200() throws Exception {
        when(learningTypeService.getById(this.learningType.getId())).thenReturn((this.learningTypeResponseDTO));
        ResultActions response = mockMvc.perform(get("/learnings/types/{id}", this.learningType.getId()));


        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString((this.learningTypeResponseDTO))));
    }
    @Test
    public void getById_DoesntExist_ThrowDoesntExist() throws Exception {
        when(learningTypeService.getById(this.learningType.getId())).thenThrow(
                new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, this.learningType.getId())
        );
        ResultActions response = mockMvc.perform(get("/learnings/types/{id}", this.learningType.getId()));


        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE, this.learningType.getId())));
 }
    @Test
    public void updateType_whenValidInput_thenReturns200() throws Exception {
        learningTypeResponseDTO.setName("New Name");
        learningTypeResponseDTO.setBaseScore(22);
        when(learningTypeService.updateType(this.learningType.getId(), this.learningTypeRequestDTO)).thenReturn(this.learningTypeResponseDTO);
        mockMvc.perform(put("/learnings/types/{id}", this.learningType.getId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningTypeRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(learningTypeResponseDTO)));
    }
    @Test
    public void updateType_TypeNotFound_ThrowsDoesNotExist() throws Exception {
        when(learningTypeService.updateType(this.learningType.getId(), this.learningTypeRequestDTO))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, this.learningType.getId()));
            mockMvc.perform(put("/learnings/types/{id}", this.learningType.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.learningTypeRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE, this.learningType.getId())));
    }
    @Test
    public void deleteLearning_Success_ReturnsOk() throws Exception {
        doNothing().when(learningTypeService).deleteType(this.learningType.getId());

        mockMvc.perform(delete("/learnings/types/")
                        .param("id", String.valueOf(this.learningTypeResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteLearning_TypeDoesntExist_ThrowsDoesntExist() throws Exception {
        doThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE,this.learningType.getId())).when(learningTypeService).deleteType(this.learningType.getId());

        mockMvc.perform(delete("/learnings/types/")
                        .param("id", String.valueOf(this.learningTypeResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DoesNotExistException))
                .andExpect(content().string(String.format(DoesNotExistException.LEARNING_TYPE, this.learningType.getId())));
    }
    @Test
    public void deleteSubject_LearningUsesSubject_ThrowsAlreadyExists() throws Exception {
        doThrow(new AlreadyExistsException(AlreadyExistsException.LEARNING_HAS_TYPE,this.learningTypeRequestDTO.getName()))
                .when(learningTypeService).deleteType(this.learningType.getId());

        mockMvc.perform(delete("/learnings/types/")
                        .param("id", String.valueOf(this.learningTypeResponseDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyExistsException))
                .andExpect(content().string(String.format(AlreadyExistsException.LEARNING_HAS_TYPE, this.learningTypeRequestDTO.getName())));
    }

}
