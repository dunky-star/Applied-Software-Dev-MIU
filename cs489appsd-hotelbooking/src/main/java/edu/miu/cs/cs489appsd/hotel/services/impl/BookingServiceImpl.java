package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.NotificationDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.entities.Booking;
import edu.miu.cs.cs489appsd.hotel.entities.Room;
import edu.miu.cs.cs489appsd.hotel.enums.BookingStatus;
import edu.miu.cs.cs489appsd.hotel.enums.PaymentStatus;
import edu.miu.cs.cs489appsd.hotel.exceptions.InvalidBookingStateAndDateException;
import edu.miu.cs.cs489appsd.hotel.exceptions.NotFoundException;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.RoomRepository;
import edu.miu.cs.cs489appsd.hotel.services.BookingCodeGenerator;
import edu.miu.cs.cs489appsd.hotel.services.BookingService;
import edu.miu.cs.cs489appsd.hotel.services.NotificationService;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingCodeGenerator bookingCodeGenerator;

    @Override
    public Mono<Response> getAllBookings() {
        return bookingRepository.findAll()
                .collectList()
                .map(bookings -> {
                    List<BookingDto> bookingDtoList = modelMapper.map(bookings, new TypeToken<List<BookingDto>>() {}.getType());
                    return Response.builder()
                            .status(200)
                            .message("success")
                            .bookings(bookingDtoList)
                            .build();
                });
    }

    @Override
    public Mono<Response> createBooking(BookingDto bookingDto) {
        return userService.getCurrentLoggedInUser()
                .flatMap(currentUser -> roomRepository.findById(bookingDto.getRoomId())
                        .switchIfEmpty(Mono.error(new NotFoundException("Room not found")))
                        .flatMap(room -> bookingRepository.isRoomAvailable(room.getId(), bookingDto.getCheckInDate(), bookingDto.getCheckOutDate())
                                .flatMap(isAvailable -> {
                                    if (!isAvailable) {
                                        return Mono.error(new InvalidBookingStateAndDateException("Room not available for selected dates"));
                                    }

                                    validateDates(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
                                    BigDecimal totalPrice = calculateTotalPrice(room, bookingDto);

                                    // FIX: properly handle Mono<String> from bookingCodeGenerator
                                    return bookingCodeGenerator.generateBookingReference()
                                            .flatMap(bookingReference -> {
                                                Booking booking = Booking.builder()
                                                        .userId(currentUser.getId())
                                                        .roomId(room.getId())
                                                        .checkInDate(bookingDto.getCheckInDate())
                                                        .checkOutDate(bookingDto.getCheckOutDate())
                                                        .totalPrice(totalPrice)
                                                        .bookingReference(bookingReference)
                                                        .paymentStatus(PaymentStatus.PENDING)
                                                        .bookingStatus(BookingStatus.BOOKED)
                                                        .build();

                                                return bookingRepository.save(booking)
                                                        .then(Mono.fromRunnable(() -> {
                                                            String paymentUrl = "http://localhost:3000/payment/" + bookingReference + "/" + totalPrice;
                                                            log.info("Payment link: {}", paymentUrl);

                                                            NotificationDto notification = NotificationDto.builder()
                                                                    .recipient(currentUser.getEmail())
                                                                    .subject("Booking Confirmation")
                                                                    .body(String.format("Your booking is successful. Payment link:\n%s", paymentUrl))
                                                                    .bookingReference(bookingReference)
                                                                    .build();
                                                            notificationService.sendEmail(notification);
                                                        }))
                                                        .thenReturn(Response.builder()
                                                                .status(201)
                                                                .message("Booking created successfully")
                                                                .build());
                                            });
                                })
                        )
                );
    }


    @Override
    public Mono<Response> getBookingByReferenceNo(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference)
                .switchIfEmpty(Mono.error(new NotFoundException("Booking not found with reference: " + bookingReference)))
                .map(booking -> {
                    BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
                    return Response.builder()
                            .status(200)
                            .message("success")
                            .booking(bookingDto)
                            .build();
                });
    }

    @Override
    public Mono<Response> updateBooking(BookingDto bookingDto) {
        if (bookingDto.getId() == null) {
            return Mono.error(new NotFoundException("Booking id is required"));
        }

        return bookingRepository.findById(bookingDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Booking not found")))
                .flatMap(existingBooking -> {
                    if (bookingDto.getBookingStatus() != null) {
                        existingBooking.setBookingStatus(bookingDto.getBookingStatus());
                    }
                    if (bookingDto.getPaymentStatus() != null) {
                        existingBooking.setPaymentStatus(bookingDto.getPaymentStatus());
                    }
                    return bookingRepository.save(existingBooking)
                            .thenReturn(Response.builder()
                                    .status(200)
                                    .message("Booking updated successfully")
                                    .build());
                });
    }

    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be before today");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new InvalidBookingStateAndDateException("Check-out date cannot be before check-in date");
        }
        if (checkInDate.isEqual(checkOutDate)) {
            throw new InvalidBookingStateAndDateException("Check-in and check-out date cannot be the same");
        }
    }

    private BigDecimal calculateTotalPrice(Room room, BookingDto bookingDto) {
        BigDecimal pricePerNight = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }
}
