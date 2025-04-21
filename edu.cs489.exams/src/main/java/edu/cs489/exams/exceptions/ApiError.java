package edu.cs489.exams.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
