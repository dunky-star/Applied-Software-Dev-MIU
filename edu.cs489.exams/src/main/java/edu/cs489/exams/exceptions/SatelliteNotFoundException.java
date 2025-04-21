package edu.cs489.exams.exceptions;

public class SatelliteNotFoundException extends RuntimeException {
    public SatelliteNotFoundException(String message) {
        super(message);
    }
}