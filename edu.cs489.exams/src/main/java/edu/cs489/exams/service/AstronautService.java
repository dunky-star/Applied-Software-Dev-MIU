package edu.cs489.exams.service;

import edu.cs489.exams.dtos.AstronautRequestDto;
import edu.cs489.exams.dtos.AstronautResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AstronautService {
    AstronautResponseDto createAstronaut(AstronautRequestDto astronautRequestDto);
    Page<AstronautResponseDto> getAllAstronauts(Pageable pageable);
}
