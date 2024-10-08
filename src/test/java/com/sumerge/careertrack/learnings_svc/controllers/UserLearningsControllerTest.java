package com.sumerge.careertrack.learnings_svc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.services.UserLearningsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserLearningsController.class ,  excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
class UserLearningsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserLearningsService userLearningsService;

    @Test
    void getAllUserLearnings_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(uuid);
        List<UserLearningResponseDTO> userLearningResponseDTOList = new ArrayList<>();
        userLearningResponseDTOList.add(userLearningResponseDTO);
        when(userLearningsService.getAllUserLearnings())
                .thenReturn(userLearningResponseDTOList);

        mockMvc.perform(get("/users-learnings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());

        verify(userLearningsService, times(1)).getAllUserLearnings();
    }

    @Test
    void getAllUserLearnings_Empty() throws Exception {
        List<UserLearningResponseDTO> userLearningResponseDTOList = new ArrayList<>();
        when(userLearningsService.getAllUserLearnings())
                .thenReturn(userLearningResponseDTOList);

        mockMvc.perform(get("/users-learnings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(userLearningsService, times(1)).getAllUserLearnings();
    }

    @Test
    void getUserLearningsById_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(uuid);

        when(userLearningsService.getUserLearningByUserLearningId(uuid))
                .thenReturn(userLearningResponseDTO);

        mockMvc.perform(get("/users-learnings/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userLearningsService, times(1))
                .getUserLearningByUserLearningId(uuid);
    }

    @Test
    void getUserLearningsById_Not_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userLearningsService.getUserLearningByUserLearningId(uuid))
                .thenThrow(DoesNotExistException.class);

        mockMvc.perform(get("/users-learnings/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1))
                .getUserLearningByUserLearningId(uuid);
    }


  //TODO needs user

//    @Test
//    void getUserLearningsByUserId_Successful() throws Exception {}

//    @Test
//    void getUserLearningsByUserId_NotFound() throws Exception {}

    @Test
    void getAllUserLearningsByLearningId_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(uuid);

        List<UserLearningResponseDTO> userLearningResponseDTOList = new ArrayList<>();
        userLearningResponseDTOList.add(userLearningResponseDTO);

        when(userLearningsService.getAllUsersLearningsByLearningId(uuid))
                .thenReturn(userLearningResponseDTOList);

        mockMvc.perform(get("/users-learnings/learning/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(userLearningsService, times(1)).getAllUsersLearningsByLearningId(uuid);
    }

    @Test
    void getUserLearningsByLearningId_Not_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userLearningsService.getAllUsersLearningsByLearningId(uuid))
                .thenThrow(DoesNotExistException.class);
        mockMvc.perform(get("/users-learnings/learning/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).getAllUsersLearningsByLearningId(uuid);
    }

    @Test
    void createUserLearning_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(uuid);

        when(userLearningsService.createUserLearning(any(UserLearningRequestDTO.class)))
                .thenReturn(userLearningResponseDTO);

        mockMvc.perform(post("/users-learnings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLearningResponseDTO)))
                .andExpect(status().isOk());

        verify(userLearningsService, times(1)).createUserLearning(any(UserLearningRequestDTO.class));
    }



    @Test
    void updateUserLearning_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(uuid);
        UserLearningRequestDTO userLearningRequestDTO = new UserLearningRequestDTO();

        when(userLearningsService.updateUserLearning(uuid, userLearningRequestDTO))
        .thenReturn(userLearningResponseDTO);
        mockMvc.perform(put("/users-learnings/{learningId}",uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLearningResponseDTO)))
                .andExpect(status().isOk());

        verify(userLearningsService , times(1)).updateUserLearning(uuid, userLearningRequestDTO);
    }
    @Test
    void updateUserLearning_Not_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        UserLearningRequestDTO userLearningRequestDTO = new UserLearningRequestDTO();

        when(userLearningsService.updateUserLearning(uuid, userLearningRequestDTO))
                .thenThrow(DoesNotExistException.class);

        mockMvc.perform(put("/users-learnings/{learningId}",uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLearningRequestDTO)))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).updateUserLearning(uuid, userLearningRequestDTO);
    }


    @Test
    void deleteUserLearning_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userLearningsService.deleteUserLearning(uuid))
                .thenReturn("Deleted Successfully");

        mockMvc.perform(delete("/users-learnings/{learningId}",uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userLearningsService, times(1)).deleteUserLearning(uuid);
    }

    @Test
    void deleteUserLearning_Not_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userLearningsService.deleteUserLearning(uuid))
                .thenThrow(DoesNotExistException.class);

        mockMvc.perform(delete("/users-learnings/{learningId}",uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).deleteUserLearning(uuid);
    }

    @Test
    void approveUserLearning_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(UUID.randomUUID());
        userLearningResponseDTO.setApprovalStatus(ApprovalStatus.APPROVED);

        when(userLearningsService.approveLearning(uuid))
                .thenReturn(userLearningResponseDTO);

        mockMvc.perform(put("/users-learnings/approve/{learningId}",uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userLearningsService, times(1)).approveLearning(uuid);
    }

    @Test
    void approveUserLearning_Not_Successful() throws Exception {

            UUID uuid = UUID.randomUUID();
            when(userLearningsService.approveLearning(uuid))
                    .thenThrow(DoesNotExistException.class);

            mockMvc.perform(put("/users-learnings/approve/{learningId}",uuid)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            verify(userLearningsService, times(1)).approveLearning(uuid);
    }

    @Test
    void rejectUserLearning_Successful() throws Exception {
        UserLearningResponseDTO userLearningResponseDTO = new UserLearningResponseDTO();
        UUID uuid = UUID.randomUUID();
        userLearningResponseDTO.setId(UUID.randomUUID());
        userLearningResponseDTO.setApprovalStatus(ApprovalStatus.REJECTED);

        when(userLearningsService.rejectLearning(uuid))
                .thenReturn(userLearningResponseDTO);

        mockMvc.perform(put("/users-learnings/reject/{learningId}",uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userLearningsService, times(1)).rejectLearning(uuid);
    }

    @Test
    void rejectUserLearning_Not_Successful() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userLearningsService.rejectLearning(uuid))
                .thenThrow(DoesNotExistException.class);

        mockMvc.perform(put("/users-learnings/reject/{learningId}",uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).rejectLearning(uuid);

    }


}