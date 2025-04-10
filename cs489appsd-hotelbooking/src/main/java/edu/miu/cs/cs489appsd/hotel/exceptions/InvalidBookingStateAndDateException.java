package edu.miu.cs.cs489appsd.hotel.exceptions;

public class InvalidBookingStateAndDateException extends RuntimeException{
    public InvalidBookingStateAndDateException(String message) {
        super(message);
    }
}
