package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Booster;
import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.UserLearning;
import com.sumerge.careertrack.learnings_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.learnings_svc.entities.enums.ApprovalStatus;
import com.sumerge.careertrack.learnings_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.learnings_svc.entities.requests.CustomUserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.requests.NotificationRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.CustomUserLearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.UserLearningMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.UserLearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.UserLearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import com.sumerge.careertrack.learnings_svc.repositories.UserLearningsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserLearningsService {

    private final UserLearningsRepository userLearningsRepository;
    private final UserLearningMapper userLearningMapper;
    private final LearningRepository learningRepository;
    private final BoosterRepository boosterRepository;
    private final ProofTypesRepository proofTypesRepository;
    private final CustomUserLearningMapper customUserLearningMapper;
    private final LearningService learningService;
    private final ProducerService producerService;

    public List<UserLearningResponseDTO> getAllUserLearnings() {
        List<UserLearning> userLearnings = userLearningsRepository.findAll();
        return userLearnings.stream().map(userLearningMapper::toResponseDTO).collect(Collectors.toList());
    }
    public List<UserLearningResponseDTO> getAllUserLearningsPaginated(Pageable pageable) {
        Page<UserLearning> userLearningsPage = userLearningsRepository.findAll(pageable);
        return userLearningsPage.getContent().stream()
                .map(userLearningMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public UserLearningResponseDTO getUserLearningByUserLearningId(UUID learningId) {
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        return userLearningMapper.toResponseDTO(userLearning);
    }

    //TODO UserNeeded & its
    public List<UserLearningResponseDTO> getUserLearningsByUserId(UUID userId) {
        //TODO find by user ID when user added
        List<UserLearning> userLearnings= userLearningsRepository.findAllById(userId);
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

    //TODO add already approved exception
    public UserLearningResponseDTO approveLearning(UUID learningId,String id){
        UUID managerId = UUID.fromString(id);
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearning.setApprovalStatus(ApprovalStatus.APPROVED);
        List<UUID> receiverId = new ArrayList<UUID>();
        receiverId.add(userLearning.getUserId());
        NotificationRequestDTO notification=createNotification(userLearning,receiverId,ActionEnum.APPROVAL,managerId,new Date());
        producerService.sendMessage(notification);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO rejectLearning(UUID learningId,String id){
        UUID managerId = UUID.fromString(id);
        UserLearning userLearning = userLearningsRepository.findById(learningId)
                .orElseThrow(() -> new DoesNotExistException(DoesNotExistException.USER_LEARNING, learningId));
        userLearning.setApprovalStatus(ApprovalStatus.REJECTED);
        List<UUID> receiverId = new ArrayList<UUID>();
        receiverId.add(userLearning.getUserId());
        NotificationRequestDTO notification=
                createNotification(userLearning,receiverId,ActionEnum.REJECTION,managerId,new Date());
        producerService.sendMessage(notification);
        return userLearningMapper.toResponseDTO(userLearningsRepository.save(userLearning));
    }

    public UserLearningResponseDTO createCustomLearning(CustomUserLearningRequestDTO customUserLearning,String id) throws Exception {
        UUID managerId = UUID.fromString(id);
        LearningRequestDTO learningRequestDTO = customUserLearningMapper.toLearningRequestDTO(customUserLearning);
        UserLearningRequestDTO userLearningRequestDTO = customUserLearningMapper.toUserLearningRequestDTO(customUserLearning);

        LearningResponseDTO learningResponse = learningService.create(learningRequestDTO);
        userLearningRequestDTO.setLearningId(learningResponse.getId());
        List<UUID> receiverId = new ArrayList<UUID>();
        receiverId.add(UUID.fromString(id));
        NotificationRequestDTO notification=
                createNotification(userLearningMapper.toUserLearning(userLearningRequestDTO),
                        receiverId,ActionEnum.SUBMISSION,userLearningRequestDTO.getUserId(),userLearningMapper.toUserLearning(userLearningRequestDTO).getDate());
        producerService.sendMessage(notification);
        return createUserLearning(userLearningRequestDTO);
    }
    public NotificationRequestDTO createNotification(UserLearning savedLearning, List<UUID> receiverId, ActionEnum actionEnum, UUID actorID, Date date) {
        return NotificationRequestDTO.builder()
                .seen(false)
                .date(date)
                .actorId(actorID)
                .entityId(savedLearning.getLearning().getId())
                .actionName(actionEnum)
                .entityTypeName(EntityTypeEnum.LEARNING)
                .receiverID(receiverId)
                .build();
    }


}
