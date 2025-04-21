package edu.cs489.exams.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SatelliteResponseDto {
    private Long id;
    private String name;
    private LocalDate launchDate;
    private String orbitType;
    private boolean decommissioned;
    private List<String> astronautNames; // NEW field for assigned astronauts
}
