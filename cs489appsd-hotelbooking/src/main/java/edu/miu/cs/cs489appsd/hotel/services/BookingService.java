package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import reactor.core.publisher.Mono;

public interface BookingService {
    Mono<Response> getAllBookings();
    Mono<Response> createBooking(BookingDto bookingDto);
    Mono<Response> getBookingByReferenceNo(String bookingReference);
    Mono<Response> updateBooking(BookingDto bookingDto);
}
