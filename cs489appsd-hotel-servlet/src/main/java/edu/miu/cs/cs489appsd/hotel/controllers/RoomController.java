package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.RoomDto;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import edu.miu.cs.cs489appsd.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Only admin can add a rooms
    public ResponseEntity<Response> addRoom(
            @ModelAttribute RoomDto roomRequest,
            @RequestParam MultipartFile imageFile
    ) {
        RoomDto roomDto = RoomDto.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .pricePerNight(roomRequest.getPricePerNight())
                .capacity(roomRequest.getCapacity())
                .description(roomRequest.getDescription())
                .build();
        Response response = roomService.addRoom(roomDto, imageFile);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(
            @ModelAttribute RoomDto roomRequest,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        RoomDto roomDto = RoomDto.builder()
                .id(roomRequest.getId())
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .pricePerNight(roomRequest.getPricePerNight())
                .capacity(roomRequest.getCapacity())
                .description(roomRequest.getDescription())
                .build();

        Response response = roomService.updateRoom(roomDto, imageFile);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllRooms() {
        Response response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long id) {
        Response response = roomService.getRoomById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long id) {
        Response response = roomService.deleteRoom(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/available")
    public ResponseEntity<Response> getAvailableRooms(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) RoomType roomType
    ) {
        Response response = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchRoom(@RequestParam String searchParam) {
        Response response = roomService.searchRoom(searchParam);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes() {
        List<RoomType> response = roomService.getAllRoomTypes();
        return ResponseEntity.ok(response);
    }


}
