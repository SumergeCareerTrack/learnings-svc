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
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningSubjectRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)

public class LearningServiceTests {


    @Mock
    private  LearningMapper learningMapper;
    @Mock
    private  LearningTypeRepository learningTypeRepository;
    @Mock
    private  LearningSubjectRepository learningSubjectRepository;
    @Mock
    private  LearningRepository learningRepository;
    @InjectMocks
    private LearningService learningService;

    LearningRequestDTO learningRequestDTO;
    Learning learning;
    LearningType learningType;
    LearningSubject learningSubject;
    LearningResponseDTO learningResponseDTO;



    @BeforeEach
    public void setUp() {
        learningType= LearningType.builder().id(UUID.randomUUID()).baseScore(10)
                .name("Type1").build();
        learningSubject= LearningSubject.builder().id(UUID.randomUUID())
                .type(SubjectType.FUNCTIONAL).name("Subject1").build();
        learning=Learning.builder().title("Title 1").id(UUID.randomUUID()).description("Description")
                .url("URL").type(learningType).subject(learningSubject).lengthInHours(22).build();
        learningRequestDTO=LearningRequestDTO.builder().title(this.learning.getTitle()).description("Description")
                .url("URL").type(learningType.getId()).subject(learningSubject.getId()).lengthInHours(22).build();
        learningResponseDTO=LearningResponseDTO.builder().title(this.learning.getTitle()).id(this.learning.getId()).typeName(this.learningType.getName())
                .subjectType(this.learningSubject.getType().toString()).typeBaseScore(this.learningType.getBaseScore())
                .url(this.learning.getUrl()).subjectName(this.learningSubject.getName())
                .description(this.learning.getDescription()).lengthInHours(this.learning.getLengthInHours()).build();
    }
    @Test
    public void create_CorrectInput_ReturnsLearningResponseDTO() throws Exception {
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenReturn(java.util.Optional.of(learningType));
        when(learningSubjectRepository.findById(learningRequestDTO.getSubject())).thenReturn(java.util.Optional.of(learningSubject));
        when(learningMapper.toLearning(learningRequestDTO)).thenReturn(learning);
        when(learningRepository.existsByUrlAndDescriptionAndTypeAndSubject(learningRequestDTO.getUrl(), learningRequestDTO.getDescription(), learningType, learningSubject)).thenReturn(false);
        when(learningRepository.save(learning)).thenReturn(learning);
        when(learningMapper.toLearningDTO(learning)).thenReturn(this.learningResponseDTO);

        LearningResponseDTO response = learningService.create(learningRequestDTO);
        assertEquals(learningRequestDTO.getDescription(), response.getDescription());
        assertNotNull(response);
        verify(learningRepository, times(1)).save(learning);
    }

    @Test
    public void create_TypeNotFound_ThrowsDoesNotExist() throws Exception{
        when(learningTypeRepository.findById(learningRequestDTO.getType()))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, learningRequestDTO.getType()));
        assertThrows(DoesNotExistException.class,() -> learningService.create(learningRequestDTO));
        verify(learningTypeRepository, times(1)).findById(learningRequestDTO.getType());

    }
    @Test
    public void create_SubjectNotFound_ThrowsDoesNotExist() throws Exception{
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenReturn(java.util.Optional.of(learningType));
        when(learningSubjectRepository.findById(learningRequestDTO.getSubject()))
                .thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING_SUBJECT, learningRequestDTO.getSubject()));
        assertThrows(DoesNotExistException.class,() -> learningService.create(learningRequestDTO));
        verify(learningSubjectRepository, times(1)).findById(learningRequestDTO.getSubject());
        verify(learningTypeRepository, times(1)).findById(learningRequestDTO.getType());


    }

    @Test
    public void create_ExistsByUrlAndDescriptionAndTypeAndSubject_AlreadyExists() throws Exception{
        when(learningMapper.toLearning(learningRequestDTO)).thenReturn(learning);
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenReturn(java.util.Optional.of(learningType));
        when(learningSubjectRepository.findById(learningRequestDTO.getSubject())).thenReturn(java.util.Optional.of(learningSubject));
        when(learningRepository.existsByUrlAndDescriptionAndTypeAndSubject(
                learningRequestDTO.getUrl(),
                learningRequestDTO.getDescription(),
                learningType,
                learningSubject
        )).thenReturn(true);

        assertThrows(AlreadyExistsException.class,() -> learningService.create(learningRequestDTO));

        verify(learningRepository, times(1)).existsByUrlAndDescriptionAndTypeAndSubject(learningRequestDTO.getUrl(), learningRequestDTO.getDescription(), learningType, learningSubject);


    }
    @Test
    public void getAll_FetchAllSuccessfully_ReturnListOfLearnings() throws Exception{
        when(learningRepository.findAll()).thenReturn(List.of(learning));
        when(learningMapper.toLearningDTO(learning)).thenReturn(learningResponseDTO);
        List<LearningResponseDTO> response = learningService.getAll();
        assertEquals(1, response.size());
        assertEquals(learningResponseDTO, response.get(0));
        verify(learningRepository, times(1)).findAll();
    }
    @Test
    public void getAll_NoneFound_ReturnsEmptyList() throws Exception{
        when(learningRepository.findAll()).thenReturn(List.of());
        List<LearningResponseDTO> response = learningService.getAll();
        assertEquals(0, response.size());
        assertEquals(List.of(), response);
        verify(learningRepository, times(1)).findAll();
    }
    @Test
    public void getLearningById_FoundLearning_ReturnsLearning() throws Exception{
        when(learningRepository.findById(learning.getId())).thenReturn(java.util.Optional.of(learning));
        when(learningMapper.toLearningDTO(learning)).thenReturn(learningResponseDTO);
        LearningResponseDTO response = learningService.getLearningById(learning.getId());
        assertEquals(learningResponseDTO, response);
        verify(learningRepository, times(1)).findById(learning.getId());
    }
    @Test
    public void getLearningById_LearningNotFound_ThrowsDoestNotExist() throws Exception{
        when(learningRepository.findById(learning.getId())).thenThrow(new DoesNotExistException(DoesNotExistException.LEARNING, learning.getId()));
        assertThrows(DoesNotExistException.class,() -> learningService.getLearningById(learning.getId()));
        verify(learningRepository, times(1)).findById(learning.getId());
    }
    @Test
    public void getLearningByType_FoundLearning_ReturnsLearning() throws Exception{
        when(learningTypeRepository.existsByName(learningType.getName())).thenReturn(true);
        when(learningTypeRepository.findByName(learningType.getName())).thenReturn(learningType);
        when(learningRepository.findByType(learningType)).thenReturn(List.of(learning));
        when(learningMapper.toLearningDTO(learning)).thenReturn(learningResponseDTO);
        List<LearningResponseDTO> response = learningService.getLearningByType(learningType.getName());
        assertEquals(1, response.size());
        assertEquals(learningResponseDTO, response.get(0));
        verify(learningRepository, times(1)).findByType(learningType);
    }

    @Test
    public void getLearningByType_TypeNoneFound_ThrowsDoesNotExist() throws Exception{
        when(learningTypeRepository.existsByName(learningType.getName())).thenReturn(false);
        assertThrows(DoesNotExistException.class,() -> learningService.getLearningByType(learningType.getName()));
        verify(learningTypeRepository, times(1)).existsByName(learningType.getName());

    }
    @Test
    public void getLearningByType_NoLearningsFound_ReturnsEmptyList() throws Exception{
        when(learningTypeRepository.existsByName(learningType.getName())).thenReturn(true);
        when(learningTypeRepository.findByName(learningType.getName())).thenReturn(learningType);
        when(learningRepository.findByType(learningType)).thenReturn(List.of());
        List<LearningResponseDTO> response = learningService.getLearningByType(learningType.getName());
        assertEquals(0, response.size());
        verify(learningRepository, times(1)).findByType(learningType);
    }

    @Test
    public void getAllLearningsBySubject_FoundLearnings_ReturnListOfLearnings() throws Exception{
        when(learningSubjectRepository.existsByName(learningSubject.getName())).thenReturn(true);
        when(learningSubjectRepository.findByName(learningSubject.getName())).thenReturn(learningSubject);
        when(learningMapper.toLearningDTO(learning)).thenReturn(learningResponseDTO);
        when(learningRepository.findBySubject(learningSubject)).thenReturn(List.of(learning));
        List<LearningResponseDTO> response = learningService.getAllLearningsBySubject(learningSubject.getName());

        assertEquals(1, response.size());
        assertEquals(learningResponseDTO, response.get(0));
        verify(learningRepository, times(1)).findBySubject(learningSubject);
    }

    @Test
    public void getAllLearningsBySubject_SubjectNoneFound_ThrowsDoesNotExist() throws Exception{
        when(learningSubjectRepository.existsByName(learningSubject.getName())).thenReturn(false);
        assertThrows(DoesNotExistException.class,() -> learningService.getAllLearningsBySubject(learningSubject.getName()));
        verify(learningSubjectRepository, times(1)).existsByName(learningSubject.getName());

    }
    @Test
    public void getAllLearningsBySubject_NoLearningsFound_ReturnsEmptyList() throws Exception{
        when(learningTypeRepository.existsByName(learningType.getName())).thenReturn(true);
        when(learningTypeRepository.findByName(learningType.getName())).thenReturn(learningType);
        when(learningRepository.findByType(learningType)).thenReturn(List.of());
        List<LearningResponseDTO> response = learningService.getLearningByType(learningType.getName());
        assertEquals(0, response.size());
        verify(learningRepository, times(1)).findByType(learningType);
    }
    @Test
    public void updateLearning_SuccessfulUpdate_ReturnUpdatedLearning() throws Exception {
        UUID learningId = UUID.randomUUID();
        LearningType type = LearningType.builder().id(UUID.randomUUID()).name("NEWType").baseScore(222).build();
        LearningSubject subject = LearningSubject.builder().id(UUID.randomUUID()).name("NEWSubject").type(SubjectType.FUNCTIONAL).build();
        learningResponseDTO.setSubjectType(subject.getType().toString());
        learningResponseDTO.setSubjectName(subject.getName());
        learningResponseDTO.setTypeName(type.getName());
        learningResponseDTO.setTypeBaseScore(type.getBaseScore());

        when(learningRepository.findById(learningId)).thenReturn(Optional.of((learning)));
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenReturn(Optional.of(type));
        when(learningSubjectRepository.findById(learningRequestDTO.getSubject())).thenReturn(Optional.of(subject));
        when(learningMapper.toLearningDTO(learning)).thenReturn(learningResponseDTO);

        LearningResponseDTO result = learningService.updateLearning(learningId, learningRequestDTO);
        assertEquals(learningResponseDTO, result);
        verify(learningRepository, times(1)).findById(learningId);
        verify(learningTypeRepository, times(1)).findById(learningRequestDTO.getType());
        verify(learningSubjectRepository, times(1)).findById(learningRequestDTO.getSubject());
        verify(learningRepository, times(1)).save(learning);
    }

    @Test
    public void updateLearning_LearningNotFound_ThrowsDoesNotExistException() {

        UUID learningId = UUID.randomUUID();
        when(learningRepository.findById(learningId)).thenReturn(Optional.empty());


        assertThrows(DoesNotExistException.class, () -> learningService.updateLearning(learningId, learningRequestDTO));

        verify(learningRepository, times(1)).findById(learningId);
        verify(learningTypeRepository, times(0)).findById(any(UUID.class));
        verify(learningSubjectRepository, times(0)).findById(any(UUID.class));
    }

    @Test
    public void updateLearning_LearningTypeNotFound_ThrowsDoesNotExistException() {
        UUID learningId = UUID.randomUUID();
        when(learningRepository.findById(learningId)).thenReturn(Optional.of(learning));
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenThrow(
                new DoesNotExistException(DoesNotExistException.LEARNING_TYPE, learningRequestDTO.getType())
        );

        assertThrows(DoesNotExistException.class, () -> learningService.updateLearning(learningId, learningRequestDTO));

        verify(learningRepository, times(1)).findById(learningId);
        verify(learningTypeRepository, times(1)).findById(learningRequestDTO.getType());
        verify(learningSubjectRepository, times(0)).findById(any(UUID.class));

    }

    @Test
    public void updateLearning_LearningSubjectNotFound_ThrowsDoesNotExistException() {
        UUID learningId = UUID.randomUUID();
        when(learningRepository.findById(learningId)).thenReturn(Optional.of(learning));
        when(learningTypeRepository.findById(learningRequestDTO.getType())).thenReturn(Optional.of(learningType));
        when(learningSubjectRepository.findById(learningRequestDTO.getSubject())).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> learningService.updateLearning(learningId, learningRequestDTO));

        verify(learningRepository, times(1)).findById(learningId);
        verify(learningTypeRepository, times(1)).findById(learningRequestDTO.getType());
        verify(learningSubjectRepository, times(1)).findById(learningRequestDTO.getSubject());
    }

    @Test
    public void deleteLearning_LearningExists_DeletesSuccessfully() {
        UUID learningId = UUID.randomUUID();
        when(learningRepository.existsById(learningId)).thenReturn(true);

        learningService.deleteLearning(learningId);

        verify(learningRepository, times(1)).existsById(learningId);
        verify(learningRepository, times(1)).deleteById(learningId);
    }

    @Test
    public void deleteLearning_LearningDoesNotExist_ThrowsDoesNotExistException() {
        UUID learningId = UUID.randomUUID();
        when(learningRepository.existsById(learningId)).thenReturn(false);

        assertThrows(DoesNotExistException.class, () -> learningService.deleteLearning(learningId));

        verify(learningRepository, times(1)).existsById(learningId);
        verify(learningRepository, times(0)).deleteById(any(UUID.class)); // Delete should not be called
    }

}
