package com.sumerge.careertrack.learnings_svc.services;

import com.sumerge.careertrack.learnings_svc.entities.ProofType;
import com.sumerge.careertrack.learnings_svc.entities.requests.ProofTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ProofTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.ProofTypeMapper;
import com.sumerge.careertrack.learnings_svc.repositories.ProofTypesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProofTypeServiceTest {

    @Mock
    private ProofTypesRepository proofTypesRepository;

    @Mock
    private ProofTypeMapper proofTypeMapper;

    @InjectMocks
    private ProofTypeService proofTypeService;

    @Test
    void getAllProofTypes_Successful() {
        ProofType proof1 = new ProofType();
        ProofType proof2 = new ProofType();

        List<ProofType> expectedTitles = Arrays.asList(proof1,proof2);

        when(proofTypesRepository.findAll()).thenReturn(expectedTitles);
        List<ProofTypeResponseDTO> receivedProofs = proofTypeService.getAllProofTypes();


        assertEquals(expectedTitles.stream()
                .map(proofTypeMapper::toResponseDTO)
                .collect(Collectors.toList()),receivedProofs );
        assertEquals(2, receivedProofs.size());

        verify(proofTypesRepository, times(1)).findAll();
    }

    @Test
    void getAllProofTypes_Empty() {
        when(proofTypesRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProofTypeResponseDTO> receivedProofs = proofTypeService.getAllProofTypes();
        assertEquals(receivedProofs.size(), 0);
        verify(proofTypesRepository, times(1)).findAll();
    }

    @Test
    void getProofTypeById_Successful() {
        ProofType proof1 = new ProofType();
        UUID uuid = UUID.randomUUID();
        proof1.setId(uuid);

        when(proofTypesRepository.findById(uuid)).thenReturn(Optional.of(proof1));
        ProofTypeResponseDTO proofType = proofTypeService.getProofTypeById(uuid);
        assertEquals(proofTypeMapper.toResponseDTO(proof1) , proofType);
        verify(proofTypesRepository, times(1)).findById(uuid);
    }

    @Test
    void getProofTypeById_Not_Successful() {
        UUID uuid = UUID.randomUUID();
        when(proofTypesRepository.findById(uuid)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class , () -> proofTypeService.getProofTypeById(uuid));
        verify(proofTypesRepository, times(1)).findById(uuid);
    }

    @Test
    void createProofType_Successful() {
        ProofTypeRequestDTO requestDTO = new ProofTypeRequestDTO();
        ProofType proofType = new ProofType();
        ProofTypeResponseDTO responseDTO = new ProofTypeResponseDTO();

        when(proofTypeMapper.toProofType(requestDTO)).thenReturn(proofType);
        when(proofTypesRepository.save(proofType)).thenReturn(proofType);
        when(proofTypeMapper.toResponseDTO(proofType)).thenReturn(responseDTO);

        ProofTypeResponseDTO result = proofTypeService.createProofType(requestDTO);

        assertNotNull(result);
        verify(proofTypesRepository, times(1)).save(proofType);
    }

    @Test
    void updateProofType_Successful() {
        UUID typeId = UUID.randomUUID();
        ProofTypeRequestDTO requestDTO = new ProofTypeRequestDTO();
        ProofType proofType = new ProofType();
        ProofTypeResponseDTO responseDTO = new ProofTypeResponseDTO();

        when(proofTypesRepository.existsById(typeId)).thenReturn(true);
        when(proofTypeMapper.toProofType(requestDTO)).thenReturn(proofType);
        when(proofTypesRepository.save(proofType)).thenReturn(proofType);
        when(proofTypeMapper.toResponseDTO(proofType)).thenReturn(responseDTO);

        ProofTypeResponseDTO result = proofTypeService.updateProofType(typeId, requestDTO);

        assertNotNull(result);
        verify(proofTypesRepository, times(1)).existsById(typeId);
        verify(proofTypesRepository, times(1)).save(proofType);
    }

    @Test
    void updateProofType_Not_Successful() {
        UUID typeId = UUID.randomUUID();
        ProofTypeRequestDTO requestDTO = new ProofTypeRequestDTO();

        when(proofTypesRepository.existsById(typeId)).thenReturn(false);

        assertThrows(DoesNotExistException.class, () -> {
            proofTypeService.updateProofType(typeId, requestDTO);
        });

        verify(proofTypesRepository, times(1)).existsById(typeId);
        verify(proofTypesRepository, never()).save(any(ProofType.class));
    }

    @Test
    void deleteProofType_Successful() {
        UUID typeId = UUID.randomUUID();
        ProofType proofType = new ProofType();

        when(proofTypesRepository.findById(typeId)).thenReturn(Optional.of(proofType));

        String result = proofTypeService.deleteProofById(typeId);

        assertEquals("Deleted Proof Successfully", result);
        verify(proofTypesRepository, times(1)).findById(typeId);
        verify(proofTypesRepository, times(1)).delete(proofType);
    }

    @Test
    void deleteProofType_Not_Successful() {
        ProofType proofType = new ProofType();
        UUID typeId = UUID.randomUUID();

        when(proofTypesRepository.findById(typeId)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class, () -> {
            proofTypeService.deleteProofById(typeId);
        });
        verify(proofTypesRepository, times(1)).findById(typeId);
        verify(proofTypesRepository, never()).delete(proofType);
    }

}