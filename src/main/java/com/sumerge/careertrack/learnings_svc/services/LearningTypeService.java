package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LearningTypeService {
    private final LearningRepository learningRep;
    private final LearningTypeMapper learningTypeMapper;
    private final LearningTypeRepository learningTypeRepository;
    //TODO 4: I think Mapper here is useless so double check!!
    public LearningTypeResponseDTO createType(LearningTypeRequestDTO learning) throws Exception {
        boolean exists = learningTypeRepository.existsByName(learning.getName());
        if(exists){
            //TODO 5: EXCEPTION HERE
            throw new Exception("Type Already Exists");
        }
        LearningType newLearning = learningTypeMapper.toLearningType(learning);
        LearningType savedLearning = learningTypeRepository.save(newLearning);
        return learningTypeMapper.toLearningTypeDTO(savedLearning);
    }

    public List<LearningTypeResponseDTO> getAll() {
        List<LearningType> types = learningTypeRepository.findAll();
        return types.stream().map(learningTypeMapper::toLearningTypeDTO).collect(java.util.stream.Collectors.toList());
    }

    public LearningTypeResponseDTO getById(UUID typeId) throws Exception {
        if(!learningTypeRepository.existsById(typeId)){
            //TODO 8: EXCEPTION HERE
            throw new Exception("Subject Not Found");
        }
        LearningType type = learningTypeRepository.findById(typeId).get();
        return learningTypeMapper.toLearningTypeDTO(type);

    }

    public LearningTypeResponseDTO updateType(UUID id,LearningTypeRequestDTO learningType) throws Exception {
        if(!learningTypeRepository.existsById(id)){
            //TODO 9: EXCEPTION HERE
            throw new Exception("Type Not Found");
        }
        LearningType type = learningTypeRepository.findById(id).get();
        type.setName(learningType.getName());
        type.setBaseScore(learningType.getBaseScore());
        LearningType savedLearning = learningTypeRepository.save(type);
        return learningTypeMapper.toLearningTypeDTO(savedLearning);
    }

    public void deleteType(UUID id) throws Exception {
        if(!learningTypeRepository.existsById(id)){
            //TODO 10: EXCEPTION HERE
            throw new Exception("Type Not Found");
        }
        LearningType type = learningTypeRepository.findById(id).get();
        List<Learning> learnings = learningRep.findByType(type);
        if(!learnings.isEmpty()){
            //TODO 11: EXCEPTION HERE
            throw new Exception("Type is in use");
        }
        else{
            learningTypeRepository.delete(type);
        }
    }
}
