package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LearningSubjectService {

    private final LearningSubjectMapper learningSubjectMapper;
    private final LearningRepository learningRep;
    private final LearningTypeRepository learningTypeRepository;
    private final LearningSubjectRepository learningSubjectRepository;

    public LearningSubjectResponseDTO createSubject(LearningSubjectRequestDTO learning) throws Exception {
        SubjectType type = learning.getType().toLowerCase().equals("functional")?SubjectType.FUNCTIONAL:SubjectType.ORGANISATIONAL;
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

    public List<LearningSubjectResponseDTO> getAllSubjects() {
        List<LearningSubject> subjects = learningSubjectRepository.findAll();
        return subjects.stream().map(learningSubjectMapper::toLearningSubjectDTO).collect(java.util.stream.Collectors.toList());
    }

    public LearningSubjectResponseDTO getSubjectById(UUID subjectId) throws Exception {
        boolean exists = learningSubjectRepository.existsById(subjectId);
        if(!exists){
            //TODO 7: EXCEPTION HERE
            throw new Exception("Subject Not Found");
        }
        LearningSubject subject = learningSubjectRepository.findById(subjectId).get();
        return learningSubjectMapper.toLearningSubjectDTO(subject);
    }

    public LearningSubjectResponseDTO updateSubject(UUID id, LearningSubjectRequestDTO learning) throws Exception {
        if(!learningSubjectRepository.existsById(id)){
            //TODO 8: EXCEPTION HERE
            throw new Exception("Subject Not Found");
        }
        LearningSubject subject = learningSubjectRepository.findById(id).get();
        subject.setName(learning.getName());
        if(learning.getType().toLowerCase().equals("functional")){
            subject.setType(SubjectType.FUNCTIONAL);
        }
        else{
            subject.setType(SubjectType.ORGANISATIONAL);
        }
        LearningSubject updatedLearning = learningSubjectRepository.save(subject);
        return learningSubjectMapper.toLearningSubjectDTO(updatedLearning);
    }

    public void deleteSubject(UUID subjectId) throws Exception {
        if(!learningSubjectRepository.existsById(subjectId)){
            //TODO 9: EXCEPTION HERE
            throw new Exception("Subject Not Found");
        }
        LearningSubject subject= learningSubjectRepository.findById(subjectId).get();
        List<Learning> learnings = learningRep.findBySubject(subject);
        if(learnings.isEmpty()){
            learningSubjectRepository.deleteById(subjectId);
        }
        else{
            //TODO 10: EXCEPTION HERE
            throw new Exception("Subject has learnings");
        }

    }
}
