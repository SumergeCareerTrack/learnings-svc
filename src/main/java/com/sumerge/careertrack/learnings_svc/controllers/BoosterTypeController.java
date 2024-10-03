package com.sumerge.careertrack.learnings_svc.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.services.BoosterTypeService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/booster/type")
@RequiredArgsConstructor
public class BoosterTypeController {

    private BoosterTypeService service;

    @GetMapping
    public ResponseEntity<List<BoosterTypeResponseDTO>> getAll() {
        List<BoosterTypeResponseDTO> types = service.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{typeName}")
    public ResponseEntity<BoosterTypeResponseDTO> getByName(@PathVariable String typeName) {
        BoosterTypeResponseDTO type = service.getByName(typeName);
        return ResponseEntity.ok(type);
    }

    @PostMapping
    public ResponseEntity<BoosterTypeResponseDTO> create(
            @RequestBody BoosterTypeRequestDTO request) {
        BoosterTypeResponseDTO newType = service.create(request);
        return ResponseEntity.ok(newType);
    }

    @PutMapping("/{typeName}")
    public ResponseEntity<BoosterTypeResponseDTO> update(@PathVariable String typeName,
            @RequestBody BoosterTypeRequestDTO request) {
        BoosterTypeResponseDTO newType = service.update(typeName, request);
        return ResponseEntity.ok(newType);
    }

    @DeleteMapping("/{typeName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLevel(@PathVariable String typeName) {
        service.delete(typeName);
    }

}
