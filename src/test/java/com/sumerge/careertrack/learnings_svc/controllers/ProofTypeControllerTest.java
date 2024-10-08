package com.sumerge.careertrack.learnings_svc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.careertrack.learnings_svc.entities.requests.ProofTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.ProofTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.services.ProofTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProofTypeController.class ,  excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
class ProofTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProofTypeService proofTypeService;

    @Test
    void getAllProofTypes_Successful() throws Exception {
        ProofTypeResponseDTO proofTypeResponseDTO = new ProofTypeResponseDTO();
        UUID proofTypeId = UUID.randomUUID();
        proofTypeResponseDTO.setId(proofTypeId);
        List<ProofTypeResponseDTO> proofTypeResponseDTOList = new ArrayList<>();
        proofTypeResponseDTOList.add(proofTypeResponseDTO);

        when(this.proofTypeService.getAllProofTypes()).thenReturn(proofTypeResponseDTOList);
        mockMvc.perform(get("/proofs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(proofTypeId.toString()));
        verify(this.proofTypeService, times(1)).getAllProofTypes();
    }

    @Test
    void getAllProofTypes_Not_Successful() throws Exception {
        List<ProofTypeResponseDTO> proofTypeResponseDTOList = new ArrayList<>();
        when(this.proofTypeService.getAllProofTypes()).thenReturn(proofTypeResponseDTOList);
        mockMvc.perform(get("/proofs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(this.proofTypeService, times(1)).getAllProofTypes();
    }

    @Test
    void getProofTypeById_Successful() throws Exception {
        UUID proofTypeId = UUID.randomUUID();
        ProofTypeResponseDTO proofTypeResponseDTO = new ProofTypeResponseDTO();
        proofTypeResponseDTO.setId(proofTypeId);

        when(proofTypeService.getProofTypeById(proofTypeId)).thenReturn(proofTypeResponseDTO);
        mockMvc.perform(get("/proofs/{proofTypeId}", proofTypeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proofTypeId.toString()));

        verify(this.proofTypeService, times(1)).getProofTypeById(proofTypeId);
    }

    @Test
    void getProofTypeById_Not_Successful() throws Exception {
        UUID proofTypeId = UUID.randomUUID();

        when(proofTypeService.getProofTypeById(proofTypeId))
                .thenThrow(new DoesNotExistException(DoesNotExistException.PROOF_TYPE, proofTypeId));
        mockMvc.perform(get("/proofs/{proofTypeId}", proofTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(this.proofTypeService, times(1)).getProofTypeById(proofTypeId);
    }

    @Test
    void createProofType_Successful() throws Exception {
        ProofTypeResponseDTO proofTypeResponseDTO = new ProofTypeResponseDTO();
        UUID proofTypeId = UUID.randomUUID();
        proofTypeResponseDTO.setId(proofTypeId);
        when(proofTypeService.createProofType(any(ProofTypeRequestDTO.class)))
                .thenReturn(proofTypeResponseDTO);

        mockMvc.perform(post("/proofs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proofTypeResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proofTypeId.toString()));

        verify(this.proofTypeService, times(1)).createProofType(any(ProofTypeRequestDTO.class));
    }

    @Test
    void updateProofType_Successful() throws Exception {
        ProofTypeRequestDTO proofTypeRequestDTO = new ProofTypeRequestDTO();
        UUID proofTypeId = UUID.randomUUID();
        ProofTypeResponseDTO proofTypeResponseDTO = new ProofTypeResponseDTO();
        proofTypeResponseDTO.setId(proofTypeId);

        when(proofTypeService.updateProofType(proofTypeId,proofTypeRequestDTO))
                .thenReturn(proofTypeResponseDTO);

        mockMvc.perform(put("/proofs/{proofTypeId}", proofTypeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proofTypeResponseDTO)))
                .andExpect(status().isOk());

        verify(this.proofTypeService, times(1)).updateProofType(proofTypeId,proofTypeRequestDTO);
    }

    @Test
    void updateProofType_Not_Successful() throws Exception {
        ProofTypeRequestDTO proofTypeRequestDTO = new ProofTypeRequestDTO();
        UUID proofTypeId = UUID.randomUUID();

        when(proofTypeService.updateProofType(proofTypeId,proofTypeRequestDTO))
                .thenThrow(new DoesNotExistException(DoesNotExistException.PROOF_TYPE , proofTypeId));

        mockMvc.perform(put("/proofs/{proofTypeId}", proofTypeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proofTypeRequestDTO)))
                .andExpect(status().isNotFound());

        verify(this.proofTypeService, times(1)).updateProofType(proofTypeId,proofTypeRequestDTO);
    }

    @Test
    void deleteProofType_Successful() throws Exception {
        UUID proofTypeId = UUID.randomUUID();
        when(proofTypeService.deleteProofById(proofTypeId))
                .thenReturn("Deleted Successfully");

        mockMvc.perform(delete("/proofs/{proofTypeId}", proofTypeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.proofTypeService, times(1)).deleteProofById(proofTypeId);
    }

    @Test
    void deleteProofType_Not_Successful() throws Exception {
        UUID proofTypeId = UUID.randomUUID();
        when(proofTypeService.deleteProofById(proofTypeId))
                .thenThrow(DoesNotExistException.class);

        mockMvc.perform(delete("/proofs/{proofTypeId}", proofTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(this.proofTypeService, times(1)).deleteProofById(proofTypeId);
    }


}