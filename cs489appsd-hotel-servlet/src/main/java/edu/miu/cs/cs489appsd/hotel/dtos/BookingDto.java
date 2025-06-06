package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.BookingStatus;
import edu.miu.cs.cs489appsd.hotel.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {

    private Long id;
    private UserDto user;
    private RoomDto room;
    private Long roomId;
    private PaymentStatus paymentStatus;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private String bookingReference; // Unique reference for the booking
    private LocalDateTime createdAt; // Date of booking creation
    private BookingStatus bookingStatus; // e.g.: CONFIRMED, CANCELED, PENDING, CHECKED_IN, CHECKED_OUT
}
