package edu.cs489.exams.controllers;

import edu.cs489.exams.dtos.SatelliteRequestDto;
import edu.cs489.exams.dtos.SatelliteResponseDto;
import edu.cs489.exams.service.impl.SatelliteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/satellites")
@AllArgsConstructor
public class SatelliteController {
    private final SatelliteService satelliteService;

    // 1. Update Satellite
    @PutMapping("/{id}")
    public ResponseEntity<SatelliteResponseDto> updateSatellite(
            @PathVariable Long id,
            @Valid @RequestBody SatelliteRequestDto satelliteRequestDto) {
        SatelliteResponseDto updatedSatellite = satelliteService.updateSatellite(id, satelliteRequestDto);
        return ResponseEntity.ok(updatedSatellite);
    }

    // Fetch Satellites with assigned Astronauts
    @GetMapping
    public ResponseEntity<List<SatelliteResponseDto>> getAllSatellites() {
        List<SatelliteResponseDto> satellites = satelliteService.getAllSatellites();
        return ResponseEntity.ok(satellites);
    }
}
