package com.sumerge.careertrack.learnings_svc.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sumerge.careertrack.learnings_svc.entities.Booster;
import com.sumerge.careertrack.learnings_svc.entities.BoosterType;
import com.sumerge.careertrack.learnings_svc.entities.requests.BoosterRequestDTO;
import com.sumerge.careertrack.learnings_svc.entities.responses.BoosterResponseDTO;
import com.sumerge.careertrack.learnings_svc.exceptions.AlreadyExistsException;
import com.sumerge.careertrack.learnings_svc.exceptions.DoesNotExistException;
import com.sumerge.careertrack.learnings_svc.mappers.BoosterMapper;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterRepository;
import com.sumerge.careertrack.learnings_svc.repositories.BoosterTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoosterService {
    private BoosterTypeRepository typeRepository;

    private BoosterMapper boosterMapper;
    private BoosterRepository boosterRepository;

    public List<BoosterResponseDTO> getAllBoosters() {
        return boosterRepository.findAll().stream()
                .map(boosterMapper::toDto).collect(Collectors.toList());
    }

    public BoosterResponseDTO getById(UUID typeId) {
        Booster type = boosterRepository.findById(typeId)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, typeId));

        return boosterMapper.toDto(type);
    }

    public BoosterResponseDTO getByName(String typeName) {
        Booster type = boosterRepository.findByName(typeName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, typeName));

        return boosterMapper.toDto(type);
    }

    public BoosterResponseDTO create(BoosterRequestDTO request) {

        if (boosterRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(
                    AlreadyExistsException.BOOSTER_TYPE, request.getName());
        }

        Booster mappedType = boosterMapper.toBooster(request);
        Booster newType = boosterRepository.save(mappedType);

        return boosterMapper.toDto(newType);
    }

    public BoosterResponseDTO update(String boosterName, BoosterRequestDTO request) {
        Booster booster = boosterRepository.findByName(boosterName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER, boosterName));

        BoosterType boosterType = typeRepository.findById(request.getType())
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER_TYPE, request.getType()));

        booster.setName(request.getName());
        booster.setType(boosterType);
        booster.setActive(request.isActive());
        boosterRepository.save(booster);

        return boosterMapper.toDto(booster);
    }

    public void delete(String boosterName) {
        Booster booster = boosterRepository.findByName(boosterName)
                .orElseThrow(() -> new DoesNotExistException(
                        DoesNotExistException.BOOSTER, boosterName));

        boosterRepository.delete(booster);
    }
}
