package com.sumerge.careertrack.learnings_svc.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.entities.BoosterType;
import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterTypeRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterTypeResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.BoosterTypeMapper;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoosterTypeService {
    private BoosterTypeMapper typeMapper;
    private BoosterTypeRepository typeRepository;

    public List<BoosterTypeResponseDTO> getAllTypes() {
        return typeRepository.findAll().stream()
                .map(typeMapper::toDto).collect(Collectors.toList());
    }

    public BoosterTypeResponseDTO getById(UUID typeId) {
        BoosterType type = typeRepository.findById(typeId)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, typeId));

        return typeMapper.toDto(type);
    }

    public BoosterTypeResponseDTO getByName(String typeName) {
        BoosterType type = typeRepository.findByName(typeName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, typeName));

        return typeMapper.toDto(type);
    }

    public BoosterTypeResponseDTO create(BoosterTypeRequestDTO request) {

        if (typeRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(
                    AlreadyExistsException.BOOSTER_TYPE, request.getName());
        }

        BoosterType mappedType = typeMapper.toBoosterType(request);
        BoosterType newType = typeRepository.save(mappedType);

        return typeMapper.toDto(newType);
    }

    public BoosterTypeResponseDTO update(String typeName, BoosterTypeRequestDTO request) {
        BoosterType boosterType = typeRepository.findByName(typeName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, typeName));

        boosterType.setName(request.getName());
        boosterType.setValue(request.getValue());
        typeRepository.save(boosterType);

        return typeMapper.toDto(boosterType);
    }

    public void delete(String typeName) {
        BoosterType boosterType = typeRepository.findByName(typeName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.LEVEL, typeName));

        typeRepository.delete(boosterType);
    }
}
