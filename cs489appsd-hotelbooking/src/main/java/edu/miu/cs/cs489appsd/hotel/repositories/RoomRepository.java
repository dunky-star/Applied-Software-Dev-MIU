package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.Room;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface RoomRepository extends ReactiveCrudRepository<Room, Long> {

    @Query("""
        SELECT * FROM rooms
        WHERE id NOT IN (
            SELECT room_id
            FROM bookings
            WHERE :checkInDate <= check_out_date
              AND :checkOutDate >= check_in_date
              AND booking_status IN ('BOOKED', 'CHECKED_IN')
        )
        AND (:roomType IS NULL OR room_type = :roomType)
    """)
    Flux<Room> findAvailableRooms(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomType") RoomType roomType
    );

    @Query("""
        SELECT * FROM rooms
        WHERE CAST(room_number AS VARCHAR) LIKE CONCAT('%', :searchParam, '%')
           OR LOWER(room_type) LIKE LOWER(CONCAT('%', :searchParam, '%'))
           OR CAST(price_per_night AS VARCHAR) LIKE CONCAT('%', :searchParam, '%')
           OR CAST(capacity AS VARCHAR) LIKE CONCAT('%', :searchParam, '%')
           OR LOWER(description) LIKE LOWER(CONCAT('%', :searchParam, '%'))
    """)
    Flux<Room> searchRooms(@Param("searchParam") String searchParam);

}
