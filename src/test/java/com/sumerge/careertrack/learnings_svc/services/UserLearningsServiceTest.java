package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.*;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
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
    private ProofTypesRepository proofTypesRepository;

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

//    @Test
//    void createUserLearning_Successful() {
//        UUID learningId = UUID.randomUUID();
//        UUID boosterId = UUID.randomUUID();
//        UUID proofId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setLearningId(learningId);
//        requestDTO.setBoosterId(boosterId);
//        requestDTO.setProofId(proofId);
//
//        UserLearning userLearning = new UserLearning();
//        Learning learning = new Learning();
//        Booster booster = new Booster(boosterId,"booster",new BoosterType(UUID.randomUUID(),"type" ,1),true);
//        ProofType proofType = new ProofType();
//
//        when(learningRepository.findById(learningId)).thenReturn(Optional.of(learning));
//        when(boosterRepository.findById(boosterId)).thenReturn(Optional.of(booster));
//        when(proofTypesRepository.findById(proofId)).thenReturn(Optional.of(proofType));
//
//        when(userLearningsRepository.save(any(UserLearning.class))).thenReturn(userLearning);
//        when(userLearningMapper.toResponseDTO(any(UserLearning.class))).thenReturn(new UserLearningResponseDTO());
//        when(userLearningMapper.toUserLearning(requestDTO)).thenReturn(userLearning);
//
//        UserLearningResponseDTO result = userLearningsService.createUserLearning(requestDTO);
//
//        assertNotNull(result);
//        verify(userLearningsRepository, times(1)).save(userLearning);
//    }

//
//    @Test
//    void createUserLearning_Not_Successful_WrongLearningId() {
//        UUID learningId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setLearningId(learningId);
//
//        when(learningRepository.findById(learningId)).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING, learningId));
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.createUserLearning(requestDTO));
//        verify(userLearningsRepository, never()).save(any(UserLearning.class));
//    }
//
//    @Test
//    void createUserLearning_Not_Successful_WrongBoosterId() {
//        UUID boosterId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setBoosterId(boosterId);
//        requestDTO.setLearningId(UUID.randomUUID());
//
//        Learning mockLearning = new Learning();
//        when(learningRepository.findById(any())).thenReturn(Optional.of(mockLearning));
//        when(boosterRepository.findById(boosterId)).thenThrow(new DoesNotExistException(DoesNotExistException.BOOSTER, boosterId));
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.createUserLearning(requestDTO));
//
//        verify(userLearningsRepository, never()).save(any(UserLearning.class));
//        verify(proofTypesRepository, never()).findById(any());
//    }
//
//    @Test
//    void createUserLearning_Not_Successful_WrongProofId() {
//        UUID proofId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setProofId(proofId);
//        requestDTO.setLearningId(UUID.randomUUID());
//        requestDTO.setBoosterId(UUID.randomUUID());
//
//        Learning mockLearning = new Learning();
//        when(learningRepository.findById(any())).thenReturn(Optional.of(mockLearning));
//
//        Booster mockBooster = new Booster(UUID.randomUUID(),"booster",new BoosterType(UUID.randomUUID(),"type" ,1),true);
//        when(boosterRepository.findById(any())).thenReturn(Optional.of(mockBooster));
//
//        when(proofTypesRepository.findById(proofId)).thenThrow(new DoesNotExistException(DoesNotExistException.PROOF_TYPE, proofId));
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.createUserLearning(requestDTO));
//
//        verify(userLearningsRepository, never()).save(any(UserLearning.class));
//    }
//
//
//    @Test
//    void updateUserLearning_Successful() {
//        UUID learningId = UUID.randomUUID();
//        UUID boosterId = UUID.randomUUID();
//        UUID proofId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setLearningId(learningId);
//        requestDTO.setBoosterId(boosterId);
//        requestDTO.setProofId(proofId);
//
//        UserLearning userLearning = new UserLearning();
//        Learning learning = new Learning();
//        Booster booster = new Booster(boosterId,"booster",new BoosterType(UUID.randomUUID(),"type" ,1),true);
//        ProofType proofType = new ProofType();
//
//        when(userLearningsRepository.findById(any())).thenReturn(Optional.of(userLearning));
//        when(learningRepository.findById(learningId)).thenReturn(Optional.of(learning));
//        when(boosterRepository.findById(boosterId)).thenReturn(Optional.of(booster));
//        when(proofTypesRepository.findById(proofId)).thenReturn(Optional.of(proofType));
//
//        when(userLearningsRepository.save(any(UserLearning.class))).thenReturn(userLearning);
//        when(userLearningMapper.toResponseDTO(any(UserLearning.class))).thenReturn(new UserLearningResponseDTO());
//
//        UserLearningResponseDTO result = userLearningsService.updateUserLearning(learningId,requestDTO);
//
//        assertNotNull(result);
//        verify(userLearningsRepository, times(1)).save(userLearning);
//    }
//
//    @Test
//    void updateUserLearning_NotFound_UserLearning() {
//        UUID learningId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//
//        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.empty());
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.updateUserLearning(learningId, requestDTO));
//        verify(userLearningsRepository).findById(learningId);
//    }
//
//    @Test
//    void updateUserLearning_NotSuccessful_WrongBoosterId() {
//        UUID learningId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setBoosterId(UUID.randomUUID());
//        requestDTO.setLearningId(UUID.randomUUID());
//
//        UserLearning existingUserLearning = new UserLearning();
//        existingUserLearning.setLearning(new Learning());
//
//        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.of(existingUserLearning));
//        when(learningRepository.findById(any())).thenReturn(Optional.of(new Learning()));
//        when(boosterRepository.findById(requestDTO.getBoosterId())).thenThrow(new DoesNotExistException(DoesNotExistException.BOOSTER, requestDTO.getBoosterId()));
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.updateUserLearning(learningId, requestDTO));
//        verify(userLearningsRepository).findById(learningId);
//        verify(boosterRepository).findById(requestDTO.getBoosterId());
//    }
//
//    @Test
//    void updateUserLearning_NotSuccessful_WrongProofId() {
//        UUID learningId = UUID.randomUUID();
//        UserLearningRequestDTO requestDTO = new UserLearningRequestDTO();
//        requestDTO.setProofId(UUID.randomUUID());
//        requestDTO.setLearningId(UUID.randomUUID());
//        requestDTO.setBoosterId(UUID.randomUUID());
//        Booster mockBooster = new Booster(UUID.randomUUID(),"booster",new BoosterType(UUID.randomUUID(),"type" ,1),true);
//        UserLearning existingUserLearning = new UserLearning();
//        existingUserLearning.setLearning(new Learning());
//        existingUserLearning.setBooster(mockBooster);
//
//        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.of(existingUserLearning));
//        when(learningRepository.findById(any())).thenReturn(Optional.of(new Learning()));
//        when(boosterRepository.findById(any())).thenReturn(Optional.of(mockBooster));
//        when(proofTypesRepository.findById(requestDTO.getProofId())).thenThrow(new DoesNotExistException(DoesNotExistException.PROOF_TYPE, requestDTO.getProofId()));
//
//        assertThrows(DoesNotExistException.class, () -> userLearningsService.updateUserLearning(learningId, requestDTO));
//        verify(userLearningsRepository).findById(learningId);
//        verify(proofTypesRepository).findById(requestDTO.getProofId());
//    }

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