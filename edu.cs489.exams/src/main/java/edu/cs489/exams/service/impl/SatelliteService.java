package edu.cs489.exams.service.impl;

import edu.cs489.exams.dtos.SatelliteRequestDto;
import edu.cs489.exams.dtos.SatelliteResponseDto;

import java.util.List;

public interface SatelliteService {
    SatelliteResponseDto updateSatellite(Long id, SatelliteRequestDto satelliteRequestDto);

    List<SatelliteResponseDto> getAllSatellites(); // New method
}
