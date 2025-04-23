package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.BookingDto;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> getAllBookings() {
        return bookingService.getAllBookings()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public Mono<ResponseEntity<Response>> createBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> updateBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.updateBooking(bookingDto)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/{reference}")
    public Mono<ResponseEntity<Response>> findBookingByReferenceNo(@PathVariable String reference) {
        return bookingService.getBookingByReferenceNo(reference)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }
}
