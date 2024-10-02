package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
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
public class LearningTypeService {
    private final LearningTypeMapper learningTypeMapper;
    private final LearningTypeRepository learningTypeRepository;
    //TODO 4: I think Mapper here is useless so double check!!
    public LearningTypeResponseDTO createType(LearningTypeRequestDTO learning) throws Exception {
        boolean exists = learningTypeRepository.existsByName(learning.getName());
        if(exists){
            //TODO 5: EXCEPTION HERE
            throw new Exception("Subject Already Exists");
        }
        LearningType newLearning = learningTypeMapper.toLearningType(learning);
        LearningType savedLearning = learningTypeRepository.save(newLearning);
        return learningTypeMapper.toLearningTypeDTO(savedLearning);
    }
}
