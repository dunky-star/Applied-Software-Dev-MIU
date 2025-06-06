package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<Response> createBooking(@RequestBody BookingDto bookingDto) {
        Response response = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateBooking(@RequestBody BookingDto bookingDto) {
        Response response = bookingService.updateBooking(bookingDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/{reference}")
    public ResponseEntity<Response> findBookingByReferenceNo(@PathVariable String reference) {
        Response response = bookingService.getBookingByReferenceNo(reference);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
