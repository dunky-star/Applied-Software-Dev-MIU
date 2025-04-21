package edu.cs489.exams.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SatelliteRequestDto {
    @NotBlank
    private String name;

    @Past(message = "Launch date must be in the past")
    private LocalDate launchDate;

    @Pattern(regexp = "LEO|MEO|GEO", message = "Orbit type must be one of: LEO, MEO, GEO")
    private String orbitType;

    private boolean decommissioned;
}
