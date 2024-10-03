package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.repositories.UserLearningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserLearningsService {

    @Autowired
    UserLearningsRepository userLearningsRepository;

    @Autowired
    UserLearningMapper userLearningMapper;

    public List<UserLearningResponseDTO> getAllUserLearnings() {
        List<UserLearning> userLearnings = userLearningsRepository.findAll();
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }

    public UserLearningResponseDTO getUserLearningsByUserLearningId(UUID learningId) {
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        return userLearningMapper.toResponseDTO(userLearning);
    }

    //TODO UserNeeded
    public List<UserLearningResponseDTO> getUserLearningsByUserId(UUID userId) {
        //TODO find by user ID when user added
        List<UserLearning> userLearnings= userLearningsRepository.findAllById(userId);
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }

    //TODO Learning Needed
//    public List<UserLearningResponseDTO> getUserLearningsByLearningId(UUID learningId) {
//        List<UserLearning> userLearnings= userLearningsRepository.findAllByLearningId(learningId);
//        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
//    }

    public UserLearningResponseDTO createUserLearning(UserLearningRequestDTO userLearningRequestDTO){
        UserLearning userLearning = userLearningMapper.toUserLearning(userLearningRequestDTO);

        //case 1 Learning doesn't exist

        //case 2 userId doesn't exist

        //case 3 boosterId doesn't exist

        //case 4 learningId alreadySubmitted and pending cannot submit another submission
//        if(userLearningsRepository.findByUserIdAndLearningId)){}

        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO updateUserLearning(UUID learningId ,UserLearningRequestDTO userLearningRequestDTO){
        UserLearning userLearning =userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));

        //case 1 Learning doesn't exist

        //case 2 userId doesn't exist

        //case 3 boosterId doesn't exist

        //case 4 Editing approval Status Not Allowed Here use the specialized endpoints

        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }


    public String deleteUserLearning(UUID learningId){
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearningsRepository.delete(userLearning);

        return "User Learning deleted";
    }

    public UserLearningResponseDTO approveLearning(UUID learningId){
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearning.setApprovalStatus(ApprovalStatus.APPROVED);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO rejectLearning(UUID learningId){
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearning.setApprovalStatus(ApprovalStatus.REJECTED);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }


}
