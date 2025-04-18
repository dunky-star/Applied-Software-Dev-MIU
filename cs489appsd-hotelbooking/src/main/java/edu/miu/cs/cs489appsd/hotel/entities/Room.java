package edu.miu.cs.cs489appsd.hotel.entities;

import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Table(name = "rooms") // R2DBC Mapping
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id // R2DBC @Id, no @GeneratedValue
    private Long id;

    @Min(value = 1, message = "Room Number must be at least 1")
    @Column("room_number")
    private Integer roomNumber;

    @NotNull(message = "Room type is required")
    @Column("room_type")
    private RoomType roomType;

    @DecimalMin(value = "0.1", message = "Price per night is required")
    @Column("price_per_night")
    private BigDecimal pricePerNight;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private String description; // additional data for the room

    @Column("image_url")
    private String imageUrl; // holds the room picture
}
