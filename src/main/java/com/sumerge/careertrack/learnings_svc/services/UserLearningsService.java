package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Booster;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import com.sumerge.careertrack.learnings_svc.entities.requests.CustomUserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.CustomUserLearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import com.sumerge.careertrack.learnings_svc.repositories.UserLearningsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor()
public class UserLearningsService {

    private final UserLearningsRepository userLearningsRepository;
    private final UserLearningMapper userLearningMapper;
    private final LearningRepository learningRepository;
    private final BoosterRepository boosterRepository;
    private final ProofTypesRepository proofTypesRepository;
    private final CustomUserLearningMapper customUserLearningMapper;
    private final LearningService learningService;

    public List<UserLearningResponseDTO> getAllUserLearnings() {
        List<UserLearning> userLearnings = userLearningsRepository.findAll();
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }

    public UserLearningResponseDTO getUserLearningByUserLearningId(UUID learningId) {
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        return userLearningMapper.toResponseDTO(userLearning);
    }

    public List<UserLearningResponseDTO> getUserLearningsByUserId(UUID userId) {
        //TODO validate userId already exist
        List<UserLearning> userLearnings= userLearningsRepository.findAllByUserId(userId);
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }

    public List<UserLearningResponseDTO> getAllUsersLearningsByLearningId(UUID learningId) {
        if(!learningRepository.existsById(learningId)){
            throw new DoesNotExistException(DoesNotExistException.LEARNING, learningId);
        }
        List<UserLearning> userLearnings= userLearningsRepository.findAllByLearning_Id(learningId);
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }

    public UserLearningResponseDTO createUserLearning(UserLearningRequestDTO userLearningRequestDTO){
        UserLearning userLearning = userLearningMapper.toUserLearning(userLearningRequestDTO);

        return userLearningMapper.toResponseDTO(updateUserObject(userLearning , userLearningRequestDTO));
    }

    //HELPER FUNCTION
    public UserLearning updateUserObject(UserLearning userLearning , UserLearningRequestDTO userLearningRequestDTO){
        //case 1 Learning doesn't exist
        Learning learning = learningRepository.findById(userLearningRequestDTO.getLearningId())
                .orElseThrow(()-> new DoesNotExistException(DoesNotExistException.LEARNING, userLearningRequestDTO.getLearningId()));

        //case 2 userId doesn't exist
        //TODO user validation should be added here

        //case 3 boosterId doesn't exist
        Booster booster = boosterRepository.findFirstByIsActiveTrue()
                .orElse(null);

        userLearning.setUserId(userLearningRequestDTO.getUserId());
        userLearning.setComment(userLearningRequestDTO.getComment());
        userLearning.setDate(userLearningRequestDTO.getDate());
        userLearning.setProof(userLearningRequestDTO.getProof());
        userLearning.setLearning(learning);
        userLearning.setBooster(booster);
        return userLearningsRepository.save(userLearning);
    }

    public UserLearningResponseDTO updateUserLearning(UUID learningId ,UserLearningRequestDTO userLearningRequestDTO){
        UserLearning userLearning =userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));

        return userLearningMapper.toResponseDTO(updateUserObject(userLearning , userLearningRequestDTO));
    }

    public String deleteUserLearning(UUID learningId){
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearningsRepository.delete(userLearning);

        return "User Learning deleted";
    }

    public UserLearningResponseDTO approveLearning(UUID learningId , String comment){

        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        Learning learning = learningRepository.findById(userLearning.getLearning().getId())
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.LEARNING, userLearning.getLearning().getId()));

        learning.setApproved(true);
        userLearning.setApprovalStatus(ApprovalStatus.APPROVED);
        userLearning.setComment(comment);
        learningRepository.save(learning);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO rejectLearning(UUID learningId , String comment){
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));

        userLearning.setApprovalStatus(ApprovalStatus.REJECTED);
        userLearning.setComment(comment);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO createCustomLearning(CustomUserLearningRequestDTO customUserLearning) throws Exception {
        LearningRequestDTO learningRequestDTO = customUserLearningMapper.toLearningRequestDTO(customUserLearning);
        UserLearningRequestDTO userLearningRequestDTO = customUserLearningMapper.toUserLearningRequestDTO(customUserLearning);
        learningRequestDTO.setApproved(false);
        LearningResponseDTO learningResponse = learningService.create(learningRequestDTO);
        userLearningRequestDTO.setLearningId(learningResponse.getId());

        return createUserLearning(userLearningRequestDTO);
    }

}
