package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningResponseDTO;
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
    private final LearningTypeMapper learningTypeMapper;
    private final LearningSubjectMapper learningSubjectMapper;
    private final LearningRepository learningRep;
    private final LearningTypeRepository learningTypeRepository;
    private final LearningSubjectRepository learningSubjectRepository;

    public LearningResponseDTO create(LearningRequestDTO learning) throws Exception {
        Learning newLearning = learningMapper.toLearning(learning);
        LearningType type = learningTypeRepository.findById(learning.getType()).orElseThrow(
                //TODO 1: EXCEPTION
                ()-> new Exception("Subject Not Found")
        );
        LearningSubject Subject = learningSubjectRepository.findById(learning.getSubject()).orElseThrow(
                //TODO 2: EXCEPTION HERE
                ()-> new Exception("Subject Not Found")
        );
        newLearning.setType(type);
        newLearning.setSubject(Subject);
        boolean exists = learningRep.existsByUrlAndDescription(learning.getUrl(), learning.getDescription());

        if(exists)
        {
            Learning found = learningRep.findByUrlAndDescription(learning.getUrl(), learning.getDescription());
            if(found.getType().equals(type) && found.getSubject().equals(Subject)){
                throw new Exception("Subject Already Exists");
            }
        }
        Learning savedLearning = learningRep.save(newLearning);
        return learningMapper.toLearningDTO(savedLearning);
    }




    public List<LearningResponseDTO> getAll() {
        List<Learning> learnings = learningRep.findAll();
        return  learnings.stream().map(learningMapper::toLearningDTO).toList();
    }


    public LearningResponseDTO getLearningById(UUID id) {
        Learning learning = learningRep.findById(id).orElseThrow(
                //TODO 3: EXCEPTION HERE
        );
        return learningMapper.toLearningDTO(learning);
    }
    //TODO: Not Sure how the Enum will turn out needs further testing !!
    public List<LearningResponseDTO> getLearningByType(String typeName) {
        LearningType learnType = learningTypeRepository.findByName(typeName);
        System.out.println(learnType);
        List<Learning> learnings = learningRep.findByType(learnType);
        return learnings.stream().map(learningMapper::toLearningDTO).toList();
    }



}
