package edu.cs489.exams.service.impl;

import edu.cs489.exams.dtos.SatelliteRequestDto;
import edu.cs489.exams.dtos.SatelliteResponseDto;
import edu.cs489.exams.entity.Satellite;
import edu.cs489.exams.enums.OrbitType;
import edu.cs489.exams.exceptions.BadRequestException;
import edu.cs489.exams.exceptions.SatelliteNotFoundException;
import edu.cs489.exams.repository.SatelliteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SatelliteServiceImpl implements SatelliteService{
    private final SatelliteRepository satelliteRepository;
    private final ModelMapper modelMapper;

    @Override
    public SatelliteResponseDto updateSatellite(Long id, SatelliteRequestDto satelliteRequestDto) {
        Satellite existing = satelliteRepository.findById(id)
                .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with ID: " + id));

        if (existing.isDecommissioned()) {
            throw new BadRequestException("Cannot update a decommissioned satellite.");
        }

        existing.setName(satelliteRequestDto.getName());
        existing.setLaunchDate(satelliteRequestDto.getLaunchDate());
        existing.setOrbitType(OrbitType.valueOf(satelliteRequestDto.getOrbitType()));
        existing.setDecommissioned(satelliteRequestDto.isDecommissioned());

        Satellite updated = satelliteRepository.save(existing);

        return modelMapper.map(updated, SatelliteResponseDto.class);
    }

    @Override
    public List<SatelliteResponseDto> getAllSatellites() {
        List<Satellite> satellites = satelliteRepository.findAll();

        return satellites.stream().map(satellite -> {
            List<String> astronautNames = satellite.getAstronauts()
                    .stream()
                    .map(astronaut -> astronaut.getFirstName() + " " + astronaut.getLastName())
                    .collect(Collectors.toList());

            return SatelliteResponseDto.builder()
                    .id(satellite.getId())
                    .name(satellite.getName())
                    .launchDate(satellite.getLaunchDate())
                    .orbitType(satellite.getOrbitType().name())
                    .decommissioned(satellite.isDecommissioned())
                    .astronautNames(astronautNames)
                    .build();
        }).toList();
    }
}
