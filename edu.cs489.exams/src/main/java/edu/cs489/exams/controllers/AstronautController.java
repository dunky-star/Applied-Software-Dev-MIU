package edu.cs489.exams.controllers;

import edu.cs489.exams.dtos.AstronautRequestDto;
import edu.cs489.exams.dtos.AstronautResponseDto;
import edu.cs489.exams.service.AstronautService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/astronauts")
@AllArgsConstructor
public class AstronautController {
    private final AstronautService astronautService;

    // 1. Create Astronaut
    @PostMapping
    public ResponseEntity<AstronautResponseDto> createAstronaut(
            @Valid @RequestBody AstronautRequestDto astronautRequestDto) {
        AstronautResponseDto response = astronautService.createAstronaut(astronautRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. List Astronauts with Pagination + Sorting
    @GetMapping
    public ResponseEntity<Page<AstronautResponseDto>> getAllAstronauts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "experienceYears") String sort,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<AstronautResponseDto> astronauts = astronautService.getAllAstronauts(pageable);
        return ResponseEntity.ok(astronauts);
    }
}
