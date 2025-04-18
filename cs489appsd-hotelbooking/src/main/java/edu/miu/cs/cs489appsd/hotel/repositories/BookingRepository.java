package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.Booking;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface BookingRepository extends ReactiveCrudRepository<Booking, Long> {
    // Fetch all bookings for a specific user (reactive version: Flux)
    Flux<Booking> findByUserId(Long userId);

    // Fetch a booking by its bookingReference (Mono, because only one)
    Mono<Booking> findByBookingReference(String bookingReference);

    // Check if a room is available for the given dates
    @Query("""
        SELECT CASE WHEN COUNT(*) = 0 THEN TRUE ELSE FALSE END
        FROM bookings
        WHERE room_id = :roomId
          AND :checkInDate <= check_out_date
          AND :checkOutDate >= check_in_date
          AND booking_status IN ('BOOKED', 'CHECKED_IN')
    """)
    Mono<Boolean> isRoomAvailable(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate
    );

}
