package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.LearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.LearningSubjectMapper;
import com.sumerge.careertrack.learnings_svc.mappers.LearningTypeMapper;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningSubjectRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LearningService {
    private final LearningMapper learningMapper;
    private final LearningRepository learningRep;
    private final LearningTypeRepository learningTypeRepository;
    private final LearningSubjectRepository learningSubjectRepository;
    private final LearningRepository learningRepository;

    public LearningResponseDTO create(LearningRequestDTO learning) throws Exception {
        Learning newLearning = learningMapper.toLearning(learning);
        LearningType type = learningTypeRepository.findById(learning.getType()).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, learning.getType())
        );
        LearningSubject Subject = learningSubjectRepository.findById(learning.getSubject()).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, learning.getSubject())
        );
        newLearning.setType(type);
        newLearning.setSubject(Subject);
        boolean exists = learningRep.existsByUrlAndDescription(learning.getUrl(), learning.getDescription());

        if(exists)
        {
            List<Learning> list = learningRep.findByUrlAndDescription(learning.getUrl(), learning.getDescription());
            if(list.size()!=1){
                throw new Exception("Multiple Learning with same URL and Description");
            }
            Learning found = list.get(0);
            if(found.getType().equals(type) && found.getSubject().equals(Subject)){
                throw new AlreadyExistsException(AlreadyExistsException.LEARNING_SUBJECT, Subject.getName());
            }
        }
        Learning savedLearning = learningRep.save(newLearning);
        return learningMapper.toLearningDTO(savedLearning);
    }




    public List<LearningResponseDTO> getAll() {
        List<Learning> learnings = learningRep.findAll();
        return  learnings.stream().map(learningMapper::toLearningDTO).toList();
    }


    public LearningResponseDTO getLearningById(UUID id) throws Exception {
        Learning learning = learningRep.findById(id).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING, id)
        );
        return learningMapper.toLearningDTO(learning);
    }
    //TODO: Not Sure how the Enum will turn out needs further testing !!
    public List<LearningResponseDTO> getLearningByType(String typeName) {
        LearningType learnType = learningTypeRepository.findByName(typeName);
        List<Learning> learnings = learningRep.findByType(learnType);
        return learnings.stream().map(learningMapper::toLearningDTO).toList();
    }


    public List<LearningResponseDTO> getAllLearningBySubject(String subject) throws Exception {
        boolean SubjectExists = learningSubjectRepository.existsByName(subject);
        if(!SubjectExists){
            throw new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, subject);

        }
        LearningSubject learnSubject = learningSubjectRepository.findByName(subject);
        List<Learning> learnings = learningRep.findBySubject(learnSubject);
        return learnings.stream().map(learningMapper::toLearningDTO).toList();

    }

    public LearningResponseDTO updateLearning(UUID id, LearningRequestDTO learning) throws Exception {
        Learning learningToUpdate = learningRepository.findById(id).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING, id)
        );
        LearningType type = learningTypeRepository.findById(learning.getType()).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, learning.getType())
        );
        LearningSubject Subject = learningSubjectRepository.findById(learning.getSubject()).orElseThrow(
                ()-> new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, learning.getSubject())
        );
        learningToUpdate.setType(type);
        learningToUpdate.setSubject(Subject);
        learningToUpdate.setDescription(learning.getDescription());
        learningToUpdate.setUrl(learning.getUrl());
        learningToUpdate.setLengthInHours(learning.getLengthInHours());
        LearningResponseDTO updatedLearning = learningMapper.toLearningDTO(learningToUpdate);
        learningRepository.save(learningToUpdate);
        return updatedLearning;


    }

    public void deleteLearning(UUID id) {
        learningRepository.deleteById(id);
    }
}
