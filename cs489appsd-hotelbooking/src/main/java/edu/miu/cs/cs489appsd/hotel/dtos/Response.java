package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    // Generic
    private int status;
    private String message;

    // For login
    private String token;
    private UserRole role;
    private Boolean isActive;
    private String expirationTime;

    // User data output
    private UserDto user;
    private List<UserDto> users;

    // Booking data output
    private BookingDto booking;
    private List<BookingDto> bookings;

    // Room data output
    private RoomDto room;
    private List<RoomDto> rooms;

    // Payment data output
    private PaymentDto payment;
    private List<PaymentDto> payments;

    // Notification data output
    private NotificationDto notification;
    private List<NotificationDto> notifications;

    // Timestamp
    private LocalDateTime timestamp;

    // Handle default timestamp manually
    public LocalDateTime getTimestamp() {
        if (timestamp == null) {
            return LocalDateTime.now();
        }
        return timestamp;
    }
}
