package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.Learning;
import com.sumerge.careertrack.learnings_svc.entities.LearningSubject;
import com.sumerge.careertrack.learnings_svc.entities.LearningType;
import com.sumerge.careertrack.learnings_svc.entities.enums.SubjectType;
import com.sumerge.careertrack.learnings_svc.entities.requests.LearningTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.LearningTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.LearningTypeMapper;
import com.sumerge.careertrack.learnings_svc.repositories.LearningRepository;
import com.sumerge.careertrack.learnings_svc.repositories.LearningTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningTypeServiceTests {
    @Mock
    private LearningRepository learningRep;
    @Mock
    private LearningTypeMapper learningTypeMapper;
    @Mock
    private LearningTypeRepository learningTypeRepository;
    @InjectMocks
    private LearningTypeService learningTypeService;
    LearningType type ;
    LearningTypeRequestDTO typeRequest;
    LearningTypeResponseDTO typeResponse;
    @BeforeEach
    public void setUp() {
        type = LearningType.builder().id(UUID.randomUUID()).name("Type Test").baseScore(123).build();
        typeRequest = LearningTypeRequestDTO.builder().name("Type Test").baseScore(123).build();
        typeResponse = LearningTypeResponseDTO.builder().id(type.getId()).name("Type Test").baseScore(123).build();
    }


    @Test
    public void createType_ValidInput_ReturnsTypeResponse() throws Exception{
        when(learningTypeRepository.existsByName(typeRequest.getName())).thenReturn(false);
        when(learningTypeMapper.toLearningType(typeRequest)).thenReturn(type);
        when(learningTypeRepository.save(type)).thenReturn(type);
        when(learningTypeMapper.toLearningTypeDTO(type)).thenReturn(typeResponse);
        LearningTypeResponseDTO response = learningTypeService.createType(typeRequest);
        assertEquals(typeResponse,response);
        verify(learningTypeRepository,times(1)).existsByName(typeRequest.getName());
    }
    @Test
    public void createType_TypeFound_ThrowsAlreadyExists() throws Exception{
        when(learningTypeRepository.existsByName(typeRequest.getName())).thenReturn(true);
        assertThrows(AlreadyExistsException.class,()->learningTypeService.createType(typeRequest));
        verify(learningTypeRepository,times(1)).existsByName(typeRequest.getName());
    }
    @Test
    public void getAll_ReturnsTypeResponseList() throws Exception{
        when(learningTypeRepository.findAll()).thenReturn(java.util.List.of(type));
        when(learningTypeMapper.toLearningTypeDTO(type)).thenReturn(typeResponse);
        assertEquals(java.util.List.of(typeResponse),learningTypeService.getAll());
    }
    @Test
    public void getAll_NoTypes_ReturnsEmptyList() throws Exception{
        when(learningTypeRepository.findAll()).thenReturn(java.util.List.of());
        assertEquals(java.util.List.of(),learningTypeService.getAll());
    }
    @Test
    public void getById_TypeFound_ReturnsTypeResponse() throws Exception{
        UUID id = UUID.randomUUID();
        when(learningTypeRepository.existsById(id)).thenReturn(true);
        when(learningTypeRepository.findById(id)).thenReturn(java.util.Optional.of(type));
        when(learningTypeMapper.toLearningTypeDTO(type)).thenReturn(typeResponse);
        LearningTypeResponseDTO response = learningTypeService.getById(id);
        assertEquals(typeResponse,response);
        verify(learningTypeRepository,times(1)).existsById(id);
    }
    @Test
    public void getById_TypeNotFound_ThrowsDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        when(learningTypeRepository.existsById(id)).thenReturn(false);
        assertThrows(DoesNotExistException.class,()->learningTypeService.getById(id));
        verify(learningTypeRepository,times(1)).existsById(id);
    }
    @Test
    public void updateType_TypeFound_ReturnsTypeResponse() throws Exception{
        UUID id = UUID.randomUUID();
        LearningTypeRequestDTO updatedType = LearningTypeRequestDTO.builder().name("Type Test Updated").baseScore(0).build();
        LearningType updated = LearningType.builder().id(id).name("Type Test Updated").baseScore(0).build();
        typeResponse.setName("Type Test Updated");
        typeResponse.setBaseScore(0);
        when(learningTypeRepository.existsById(id)).thenReturn(true);
        when(learningTypeRepository.findById(id)).thenReturn(java.util.Optional.of(type));
        when(learningTypeRepository.save(type)).thenReturn(updated);
        when(learningTypeMapper.toLearningTypeDTO(updated)).thenReturn(typeResponse);
        LearningTypeResponseDTO response = learningTypeService.updateType(id,updatedType);
        assertEquals(typeResponse,response);
        verify(learningTypeRepository,times(1)).existsById(id);
        verify(learningTypeRepository,times(1)).save(type);
    }
    @Test
    public void updateType_TypeNotFound_ThrowsDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        LearningTypeRequestDTO updatedType = LearningTypeRequestDTO.builder().name("Type Test Updated").baseScore(0).build();
        when(learningTypeRepository.existsById(id)).thenReturn(false);
        assertThrows(DoesNotExistException.class,()->learningTypeService.updateType(id,updatedType));
        verify(learningTypeRepository,times(1)).existsById(id);
    }
    @Test
    public void deleteType_TypeFound_NoLearnings_DeletesType() throws Exception{
        UUID id = UUID.randomUUID();
        when(learningTypeRepository.existsById(id)).thenReturn(true);
        when(learningTypeRepository.findById(id)).thenReturn(java.util.Optional.of(type));
        when(learningRep.findByType(type)).thenReturn(java.util.List.of());
        learningTypeService.deleteType(id);
        verify(learningTypeRepository,times(1)).delete(type);
    }
    @Test
    public void deleteType_TypeFound_HasLearnings_ThrowsAlreadyExists() throws Exception{
        UUID id = UUID.randomUUID();
        LearningSubject subject=LearningSubject.builder().id(UUID.randomUUID()).name("Subject Test").type(SubjectType.FUNCTIONAL).build();
        Learning learning = Learning.builder().id(UUID.randomUUID()).subject(subject).type(type).lengthInHours(123).url("URL")
                .description("DESC").build();
        when(learningTypeRepository.existsById(id)).thenReturn(true);
        when(learningTypeRepository.findById(id)).thenReturn(java.util.Optional.of(type));
        when(learningRep.findByType(type)).thenReturn(List.of(learning));
        assertThrows(AlreadyExistsException.class,()->learningTypeService.deleteType(id));
        verify(learningRep,times(1)).findByType(type);

    }
    @Test
    public void deleteType_TypeNotFound_ThrowsDoesNotExist() throws Exception{
        UUID id = UUID.randomUUID();
        when(learningTypeRepository.existsById(id)).thenReturn(false);
        assertThrows(DoesNotExistException.class,()->learningTypeService.deleteType(id));
        verify(learningTypeRepository,times(1)).existsById(id);
    }

}
