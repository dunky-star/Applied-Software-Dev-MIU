package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.RoomDto;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Response addRoom(RoomDto roomDto, MultipartFile imageFile);
    Response updateRoom(RoomDto roomDto, MultipartFile imageFile);
    Response getAllRooms();
    Response getRoomById(Long id);
    Response deleteRoom(Long id);
    Response getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType);
    List<RoomType> getAllRoomTypes();
    Response searchRoom(String searchParam);
}
