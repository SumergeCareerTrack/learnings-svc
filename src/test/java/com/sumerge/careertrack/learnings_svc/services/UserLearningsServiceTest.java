package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.*;
import com.sumerge.careertrack.learnings_svc.entities.requests.CustomUserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.CustomUserLearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningMapper;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import com.sumerge.careertrack.learnings_svc.repositories.UserLearningsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLearningsServiceTest {

    @Mock
    private UserLearningsRepository userLearningsRepository;
    @Mock
    private UserLearningMapper userLearningMapper;
    @Mock
    private LearningRepository learningRepository;
    @Mock
    private BoosterRepository boosterRepository;
    @Mock
    private LearningService learningService;

    @Mock
    private CustomUserLearningMapper customUserLearningMapper;

    @InjectMocks
    private UserLearningsService userLearningsService;

    @Test
    void getAllUserLearnings_Successful(){
        UserLearning userLearning1 = new UserLearning();
        UserLearning userLearning2 = new UserLearning();

        List<UserLearning> userLearnings = Arrays.asList(userLearning1, userLearning2);
        when(userLearningsRepository.findAll()).thenReturn(userLearnings);
        List<UserLearningResponseDTO> receivedUserLearnings = userLearningsService.getAllUserLearnings();

        assertEquals(userLearnings.stream().map(userLearningMapper ::toResponseDTO)
                .collect(Collectors.toList()),receivedUserLearnings);
        assertEquals(2, receivedUserLearnings.size());
        verify(userLearningsRepository, times(1)).findAll();
    }

    @Test
    void getAllUserLearnings_Empty(){
        when(userLearningsRepository.findAll()).thenReturn(Collections.emptyList());
        List <UserLearningResponseDTO> userLearnings = userLearningsService.getAllUserLearnings();
        assertEquals(0, userLearnings.size());
        verify(userLearningsRepository, times(1)).findAll();
    }

    @Test
    void getUserLearningByUserLearningId_Successful(){
        UserLearning userLearning1 = new UserLearning();
        UUID uuid = UUID.randomUUID();
        userLearning1.setId(uuid);

        when(userLearningsRepository.findById(uuid)).thenReturn(Optional.of(userLearning1));

        UserLearningResponseDTO responseDTO = userLearningsService.getUserLearningByUserLearningId(uuid);

        assertEquals(userLearningMapper.toResponseDTO(userLearning1),responseDTO);
        verify(userLearningsRepository, times(1)).findById(uuid);
    }

    @Test
    void getUserLearningByUserLearningId_Not_Successful(){
        UUID uuid = UUID.randomUUID();
        when(userLearningsRepository.findById(uuid)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class,() -> userLearningsService.getUserLearningByUserLearningId(uuid));
        verify(userLearningsRepository, times(1)).findById(uuid);
    }

    @Test
    void getAllUsersLearningsByLearningId_Successful(){

        UserLearning userLearning1 = new UserLearning();
        UserLearning userLearning2 = new UserLearning();
        List<UserLearning> userLearnings = Arrays.asList(userLearning1, userLearning2);
        UUID uuid = UUID.randomUUID();
        when(learningRepository.existsById(uuid)).thenReturn(true);
        when(userLearningsRepository.findAllByLearning_Id(uuid)).thenReturn(userLearnings);

        List <UserLearningResponseDTO> receivedUserLearnings =
                userLearningsService.getAllUsersLearningsByLearningId(uuid);

        assertEquals(userLearnings.stream()
                .map(userLearningMapper::toResponseDTO)
                .collect(Collectors.toList()) , receivedUserLearnings);

        verify(learningRepository, times(1)).existsById(uuid);
        verify(userLearningsRepository, times(1)).findAllByLearning_Id(uuid);
    }

    @Test
    void getAllUsersLearningsByLearningId_Not_Successful(){
        UUID uuid = UUID.randomUUID();
        when(learningRepository.existsById(uuid)).thenThrow(DoesNotExistException.class);

        assertThrows(DoesNotExistException.class ,
                () -> userLearningsService.getAllUsersLearningsByLearningId(uuid));
        verify(learningRepository, times(1)).existsById(uuid);
        verify(userLearningsRepository, never()).findAllByLearning_Id(uuid);
    }

    //TODO tests of update , create  learning and custom learning

    @Test
    void deleteUserLearning_Successful(){
        UserLearning userLearning1 = new UserLearning();
        UUID uuid = UUID.randomUUID();
        userLearning1.setId(uuid);
        when(userLearningsRepository.findById(uuid)).thenReturn(Optional.of(userLearning1));
        String result = userLearningsService.deleteUserLearning(uuid);

        assertEquals("User Learning deleted" , result);

        verify(userLearningsRepository, times(1)).findById(uuid);
        verify(userLearningsRepository, times(1)).delete(userLearning1);

    }

    @Test
    void deleteUserLearning_Not_Successful(){
        UUID uuid = UUID.randomUUID();
        when(userLearningsRepository.findById(uuid)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class , () -> userLearningsService.deleteUserLearning(uuid));
        verify(userLearningsRepository, times(1)).findById(uuid);
        verify(userLearningsRepository ,never()).delete(any(UserLearning.class));
    }

    @Test
    void approveLearning_Successful(){
        UserLearning userLearning1 = new UserLearning();
        UUID uuid = UUID.randomUUID();
        userLearning1.setId(uuid);
        when(userLearningsRepository.findById(uuid)).thenReturn(Optional.of(userLearning1));
        userLearningsService.approveLearning(uuid);
        verify(userLearningsRepository, times(1)).findById(uuid);
        verify(userLearningsRepository, times(1)).save(userLearning1);
    }

    @Test
    void approveLearning_Not_Successful(){
        UUID uuid = UUID.randomUUID();
        when(userLearningsRepository.findById(uuid)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class , () -> userLearningsService.deleteUserLearning(uuid));
        verify(userLearningsRepository, times(1)).findById(uuid);
    }

    @Test
    void rejectLearning_Successful(){
        UserLearning userLearning1 = new UserLearning();
        UUID uuid = UUID.randomUUID();
        userLearning1.setId(uuid);
        when(userLearningsRepository.findById(uuid)).thenReturn(Optional.of(userLearning1));
        userLearningsService.rejectLearning(uuid);
        verify(userLearningsRepository, times(1)).findById(uuid);
        verify(userLearningsRepository, times(1)).save(userLearning1);
    }

    @Test
    void rejectLearning_Not_Successful(){
        UUID uuid = UUID.randomUUID();
        when(userLearningsRepository.findById(uuid)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class , () -> userLearningsService.deleteUserLearning(uuid));
        verify(userLearningsRepository, times(1)).findById(uuid);
    }

}