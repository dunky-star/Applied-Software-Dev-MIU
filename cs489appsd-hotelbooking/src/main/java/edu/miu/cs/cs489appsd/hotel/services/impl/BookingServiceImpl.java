package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.NotificationDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.entities.Booking;
import edu.miu.cs.cs489appsd.hotel.entities.Room;
import edu.miu.cs.cs489appsd.hotel.entities.User;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private final BookingService bookingService;

    @Override
    public Response createBooking(BookingDto bookingDto) {
        User currentUser = userService.getCurrentLoggedInUser();
        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Validation: Ensure the check-in date is not before today
        if (bookingDto.getCheckInDate().isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be before today");
        }

        // Validation: Ensure the check-out date is not before check-in date
        if (bookingDto.getCheckOutDate().isBefore(bookingDto.getCheckInDate())) {
            throw new InvalidBookingStateAndDateException("Check-out date cannot be before check-in date");
        }

        // Validation: Ensure the check-in date is not the same as the check-out date
        if (bookingDto.getCheckInDate().isEqual(bookingDto.getCheckOutDate())) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be equal to check-out date");
        }
        // Validation: Ensure the room is available for the selected dates
        boolean isAvailable = bookingRepository.isRoomAvailable(room.getId(), bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate());
        if (!isAvailable) {
            throw new InvalidBookingStateAndDateException("Room is not available for the selected dates");
        }
        // Calculation: Calculate the total price based on the number of nights and room price
        BigDecimal totalPrice = calculateTotalPrice(room, bookingDto);
        String bookingReference = bookingCodeGenerator.generateBookingReference();

        // Create and save the booking
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setBookingReference(bookingReference);
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        bookingRepository.save(booking);

        // Generate the payment URL which will be sent via email
        String paymentUrl = "http://localhost:3000/payment/" + bookingReference + "/" + totalPrice;

        log.info("PAYMENT LINK: {}", paymentUrl);

       // Send notification via email
        NotificationDto notificationDTO = NotificationDto.builder()
                .recipient(currentUser.getEmail())
                .subject("Booking Confirmation")
                .body(String.format(
                        "Your booking has been created successfully. Please proceed with your payment using the payment link below:\n%s",
                        paymentUrl))
                .bookingReference(bookingReference)
                .build();

        notificationService.sendEmail(notificationDTO); // Send the email

        return Response.builder()
                .status(200)
                .message("Booking created successfully")
                .booking(bookingDto)
                .build();
    }

    @Override
    public Response getBookingByReferenceNo(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new NotFoundException("Booking with reference No: " + bookingReference + " Not found"));

        BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);

        return Response.builder()
                .status(200)
                .message("success")
                .booking(bookingDto)
                .build();
    }

    @Override
    public Response updateBooking(BookingDto bookingDto) {
        if (bookingDto.getId() == null)
            throw new NotFoundException("Booking id is required");

        Booking existingBooking = bookingRepository.findById(bookingDto.getId())
                .orElseThrow(() -> new NotFoundException("Booking Not Found"));

        if (bookingDto.getBookingStatus() != null) {
            existingBooking.setBookingStatus(bookingDto.getBookingStatus());
        }

        if (bookingDto.getPaymentStatus() != null) {
            existingBooking.setPaymentStatus(bookingDto.getPaymentStatus());
        }

        bookingRepository.save(existingBooking);

        return Response.builder()
                .status(200)
                .message("Booking Updated Successfully")
                .build();
    }

    private BigDecimal calculateTotalPrice(Room room, BookingDto bookingDto) {
        BigDecimal pricePerNight = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(bookingDto.getCheckInDate(), bookingDto.getCheckOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }
}
