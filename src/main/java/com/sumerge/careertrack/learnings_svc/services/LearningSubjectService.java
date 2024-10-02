package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import com.sumerge.careertrack.learnings_svc.mappers.LearningMapper;
import com.sumerge.careertrack.learnings_svc.mappers.LearningSubjectMapper;
import com.sumerge.careertrack.learnings_svc.mappers.LearningTypeMapper;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningSubjectRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LearningSubjectService {
    private final LearningMapper learningMapper;
    private final LearningTypeMapper learningTypeMapper;
    private final LearningSubjectMapper learningSubjectMapper;
    private final LearningRepository learningRep;
    private final LearningTypeRepository learningTypeRepository;
    private final LearningSubjectRepository learningSubjectRepository;

    public LearningSubjectResponseDTO createSubject(LearningSubjectRequestDTO learning) throws Exception {
        SubjectType type = SubjectType.valueOf(learning.getType().toUpperCase());
        boolean exists = learningSubjectRepository.existsByTypeAndName(type,learning.getName());
        if(exists){
            //TODO 6: EXCEPTION HERE
            throw new Exception("Subject Already Exists");
        }

        LearningSubject newLearning = learningSubjectMapper.toLearningSubject(learning);
        if(learning.getType().toLowerCase().equals("functional")){
            newLearning.setType(SubjectType.FUNCTIONAL);
        }
        else{
            newLearning.setType(SubjectType.ORGANISATIONAL);
        }
        LearningSubject savedLearning = learningSubjectRepository.save(newLearning);
        return learningSubjectMapper.toLearningSubjectDTO(savedLearning);
    }
}
