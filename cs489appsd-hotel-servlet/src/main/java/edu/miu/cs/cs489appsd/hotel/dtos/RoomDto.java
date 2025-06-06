package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto {
    private Long id;
    private Integer roomNumber;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private String description; // additional data for the room
    private String imageUrl; // this will hold the room picture
}
