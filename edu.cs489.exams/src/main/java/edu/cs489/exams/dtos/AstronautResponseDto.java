package edu.cs489.exams.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AstronautResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int experienceYears;
    private List<SatelliteResponseDto> satellites;
}
