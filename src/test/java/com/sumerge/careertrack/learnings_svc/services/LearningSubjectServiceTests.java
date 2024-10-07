package com.sumerge.careertrack.learnings_svc.services;


import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningSubjectRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningSubjectResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.LearningSubjectMapper;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningSubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class LearningSubjectServiceTests {
    @Mock
    private LearningSubjectMapper learningSubjectMapper;
    @Mock
    private LearningRepository learningRep;
    @Mock
    private LearningSubjectRepository learningSubjectRepository;
    @InjectMocks
    private LearningSubjectService learningSubjectService;
    LearningSubject learningSubject;
    LearningSubjectRequestDTO learningSubjectRequestDTO;
    LearningSubjectResponseDTO learningSubjectResponseDTO;



    @BeforeEach
    public void setUp() {
        learningSubject = LearningSubject.builder().id(UUID.randomUUID()).name("Functional").type(SubjectType.FUNCTIONAL).build();
        learningSubjectRequestDTO = LearningSubjectRequestDTO.builder().name("Functional").type("Functional").build();
        learningSubjectResponseDTO = LearningSubjectResponseDTO.builder().id(learningSubject.getId()).name("Functional").type("Functional").build();

    }


    @Test
    public void createSubject_ValidInput_ReturnsTypeResponse() throws Exception {
        when(learningSubjectRepository.existsByTypeAndName(learningSubject.getType(), learningSubject.getName())).thenReturn(false);
        when(learningSubjectMapper.toLearningSubject(learningSubjectRequestDTO)).thenReturn(learningSubject);
        when(learningSubjectRepository.save(learningSubject)).thenReturn(learningSubject);
        when(learningSubjectMapper.toLearningSubjectDTO(learningSubject)).thenReturn(learningSubjectResponseDTO);
        LearningSubjectResponseDTO response = learningSubjectService.createSubject(learningSubjectRequestDTO);
        assertEquals(learningSubjectResponseDTO, response);
        verify(learningSubjectRepository,times(1)).save(learningSubject);
    }
    @Test
    public void createSubject_TypeFound_ThrowsAlreadyExists() throws Exception {
        when(learningSubjectRepository.existsByTypeAndName(learningSubject.getType(), learningSubject.getName())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> learningSubjectService.createSubject(learningSubjectRequestDTO));
        verify(learningSubjectRepository,times(1)).existsByTypeAndName(learningSubject.getType(), learningSubject.getName());
    }
    @Test
    public void getAll_ReturnsTypeResponseList() throws Exception {
        when(learningSubjectRepository.findAll()).thenReturn(java.util.List.of(learningSubject));
        when(learningSubjectMapper.toLearningSubjectDTO(learningSubject)).thenReturn(learningSubjectResponseDTO);
        assertEquals(java.util.List.of(learningSubjectResponseDTO), learningSubjectService.getAllSubjects());
        verify(learningSubjectRepository,times(1)).findAll();
    }
    @Test
    public void getAll_NoTypes_ReturnsEmptyList() throws Exception {
        when(learningSubjectRepository.findAll()).thenReturn(java.util.List.of());
        assertEquals(java.util.List.of(), learningSubjectService.getAllSubjects());
        verify(learningSubjectRepository,times(1)).findAll();
    }
    @Test
    public void getSubjectById_FindsSubject_ReturnsSubject() throws Exception{
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(true);
        when(learningSubjectRepository.findById(learningSubject.getId())).thenReturn(java.util.Optional.of(learningSubject));
        when(learningSubjectMapper.toLearningSubjectDTO(learningSubject)).thenReturn(learningSubjectResponseDTO);
        assertEquals(learningSubjectResponseDTO,learningSubjectService.getSubjectById(learningSubject.getId()));
        verify(learningSubjectRepository,times(1)).existsById(learningSubject.getId());
    }
    @Test
    public void getSubjectById_SubjectNotFound_ThrowsDoesNotExist() throws Exception{
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(false);
        assertThrows(DoesNotExistException.class,()->learningSubjectService.getSubjectById(learningSubject.getId()));
        verify(learningSubjectRepository,times(1)).existsById(learningSubject.getId());
    }
    @Test
    public void updateSubject_ValidId_UpdatesSubjectSuccessfully() throws Exception {
        UUID subjectId = learningSubject.getId();
        LearningSubjectRequestDTO updateRequest = LearningSubjectRequestDTO.builder()
                .name("Updated Subject")
                .type("organisational")
                .build();
        LearningSubjectResponseDTO updatedResponse = LearningSubjectResponseDTO.builder()
                .name("Updated Subject")
                .type("organisational")
                .build();

        when(learningSubjectRepository.existsById(subjectId)).thenReturn(true);
        when(learningSubjectRepository.findById(subjectId)).thenReturn(Optional.of(learningSubject));
        when(learningSubjectMapper.toLearningSubjectDTO(any())).thenReturn(updatedResponse);

        LearningSubjectResponseDTO response = learningSubjectService.updateSubject(subjectId, updateRequest);

        assertNotNull(response);
        assertEquals("Updated Subject", learningSubject.getName());
        assertEquals(SubjectType.ORGANISATIONAL, learningSubject.getType());

        verify(learningSubjectRepository,times(1)).save(learningSubject);
    }
    @Test
    public void updateSubject_SubjectNotFound_ThrowsDoesNotExistException() {
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(false);

        assertThrows(DoesNotExistException.class, () -> {
            learningSubjectService.updateSubject(learningSubject.getId(), any());
        });
        verify(learningSubjectRepository, times(1)).existsById(learningSubject.getId());
    }

    @Test
    public void deleteSubject_ValidId_DeletesSubjectSuccessfully() throws Exception {
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(true);
        when(learningSubjectRepository.findById(learningSubject.getId())).thenReturn(Optional.of(this.learningSubject));
        when(learningRep.findBySubject(this.learningSubject)).thenReturn(List.of());

        learningSubjectService.deleteSubject(learningSubject.getId());

        verify(learningSubjectRepository,times(1)).deleteById(learningSubject.getId());
    }
    @Test
    public void deleteSubject_SubjectNotFound_ThrowsDoesNotExistException() {
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(false);

        assertThrows(DoesNotExistException.class, () -> {
            learningSubjectService.deleteSubject(learningSubject.getId());
        });
        verify(learningSubjectRepository, times(1)).existsById(learningSubject.getId());
    }
    @Test
    public void deleteSubject_SubjectHasLearnings_ThrowsAlreadyExistsException() {
        LearningType type = LearningType.builder().id(UUID.randomUUID()).name("Functional").baseScore(0).build();
        Learning learning = Learning.builder().id(UUID.randomUUID()).subject(learningSubject).type(type).lengthInHours(123).url("URL")
                .description("DESC").build();
        when(learningSubjectRepository.existsById(learningSubject.getId())).thenReturn(true);
        when(learningSubjectRepository.findById(learningSubject.getId())).thenReturn(Optional.of(this.learningSubject));
        when(learningRep.findBySubject(this.learningSubject)).thenReturn(List.of(learning));

        assertThrows(AlreadyExistsException.class, () -> {
            learningSubjectService.deleteSubject(learningSubject.getId());
        });
        verify(learningSubjectRepository, times(1)).existsById(learningSubject.getId());
    }
}
