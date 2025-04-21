package edu.cs489.exams.exceptions;

public class AstronautNotFoundException extends RuntimeException{
    public AstronautNotFoundException(String message) {
        super(message);
    }
}
