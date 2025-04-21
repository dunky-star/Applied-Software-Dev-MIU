package edu.cs489.exams.service.impl;

import edu.cs489.exams.dtos.AstronautRequestDto;
import edu.cs489.exams.dtos.AstronautResponseDto;
import edu.cs489.exams.dtos.SatelliteResponseDto;
import edu.cs489.exams.entity.Astronaut;
import edu.cs489.exams.entity.Satellite;
import edu.cs489.exams.exceptions.BadRequestException;
import edu.cs489.exams.repository.AstronautRepository;
import edu.cs489.exams.repository.SatelliteRepository;
import edu.cs489.exams.service.AstronautService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AstronautServiceImpl implements AstronautService {

    private final AstronautRepository astronautRepository;
    private final SatelliteRepository satelliteRepository;
    private final ModelMapper modelMapper;

    @Override
    public AstronautResponseDto createAstronaut(AstronautRequestDto astronautRequestDto) {
        // Validate satellites exist
        List<Long> satelliteIds = astronautRequestDto.getSatelliteIds();
        List<Satellite> satellites = satelliteRepository.findAllById(satelliteIds);

        if (satellites.size() != satelliteIds.size()) {
            throw new BadRequestException("One or more satellites not found.");
        }

        // Map DTO to Entity
        Astronaut astronaut = new Astronaut();
        astronaut.setFirstName(astronautRequestDto.getFirstName());
        astronaut.setLastName(astronautRequestDto.getLastName());
        astronaut.setExperienceYears(astronautRequestDto.getExperienceYears());
        astronaut.setSatellites(new ArrayList<>(satellites));

        // Save
        Astronaut savedAstronaut = astronautRepository.save(astronaut);

        // Manual Mapping to DTO
        AstronautResponseDto response = AstronautResponseDto.builder()
                .id(savedAstronaut.getId())
                .firstName(savedAstronaut.getFirstName())
                .lastName(savedAstronaut.getLastName())
                .experienceYears(savedAstronaut.getExperienceYears())
                .satellites(savedAstronaut.getSatellites()
                        .stream()
                        .map(satellite -> SatelliteResponseDto.builder()
                                .id(satellite.getId())
                                .name(satellite.getName())
                                .launchDate(satellite.getLaunchDate())
                                .orbitType(satellite.getOrbitType().name())
                                .decommissioned(satellite.isDecommissioned())
                                .build())
                        .toList())
                .build();

        return response;
    }


    @Override
    public Page<AstronautResponseDto> getAllAstronauts(Pageable pageable) {
        return astronautRepository.findAll(pageable)
                .map(astronaut -> modelMapper.map(astronaut, AstronautResponseDto.class));
    }
}
