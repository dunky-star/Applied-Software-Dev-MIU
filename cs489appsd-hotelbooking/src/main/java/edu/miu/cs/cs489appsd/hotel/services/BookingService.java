package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;

public interface BookingService {
    Response createBooking(BookingDto bookingDto);
    Response getBookingByReferenceNo(String bookingReference);
    Response updateBooking(BookingDto bookingDto);
}
