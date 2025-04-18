package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.RoomDto;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface RoomService {
    Mono<Response> addRoom(RoomDto roomDto, MultipartFile imageFile);
    Mono<Response> updateRoom(RoomDto roomDto, MultipartFile imageFile);
    Mono<Response> getAllRooms();
    Mono<Response> getRoomById(Long id);
    Mono<Response> deleteRoom(Long id);
    Mono<Response> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType);
    Flux<RoomType> getAllRoomTypes();
    Mono<Response> searchRoom(String searchParam);
}
