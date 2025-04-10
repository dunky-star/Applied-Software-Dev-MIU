package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    // generic
    private int status;
    private String message;

    // for login
    private String token;
    private UserRole role;
    private Boolean isActive;
    private String expirationTime;

    // user data output
    private UserDto user;
    private List<UserDto> users;

    // booking data output
    private BookingDto booking;
    private List<BookingDto> bookings;

    // room data output
    private RoomDto room;
    private List<RoomDto> rooms;

    // Payment data output
    private PaymentDto payment;
    private List<PaymentDto> payments;

    // Notification data output
    private NotificationDto notification;
    private List<NotificationDto> notifications;

    // Timestamp
    private final LocalDateTime timestamp = LocalDateTime.now();
}

