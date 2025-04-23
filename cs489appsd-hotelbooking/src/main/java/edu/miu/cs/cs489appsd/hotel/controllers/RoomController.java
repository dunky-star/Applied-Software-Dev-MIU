package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.RoomDto;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import edu.miu.cs.cs489appsd.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> addRoom(
            @ModelAttribute RoomDto roomRequest,
            @RequestPart("imageFile") Mono<FilePart> imageFileMono
    ) {
        RoomDto roomDto = RoomDto.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .pricePerNight(roomRequest.getPricePerNight())
                .capacity(roomRequest.getCapacity())
                .description(roomRequest.getDescription())
                .build();

        return imageFileMono
                .flatMap(filePart -> roomService.addRoom(roomDto, filePart))
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> updateRoom(
            @ModelAttribute RoomDto roomRequest,
            @RequestPart(value = "imageFile", required = false) Mono<FilePart> imageFileMono
    ) {
        RoomDto roomDto = RoomDto.builder()
                .id(roomRequest.getId())
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .pricePerNight(roomRequest.getPricePerNight())
                .capacity(roomRequest.getCapacity())
                .description(roomRequest.getDescription())
                .build();

        // handle the case where image is provided or not
        return imageFileMono
                .defaultIfEmpty(null) // makes it optional
                .flatMap(filePart -> roomService.updateRoom(roomDto, filePart))
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }


    @GetMapping
    public Mono<ResponseEntity<Response>> getAllRooms() {
        return roomService.getAllRooms()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response>> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/available")
    public Mono<ResponseEntity<Response>> getAvailableRooms(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) RoomType roomType
    ) {
        return roomService.getAvailableRooms(checkInDate, checkOutDate, roomType)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<Response>> searchRoom(@RequestParam String searchParam) {
        return roomService.searchRoom(searchParam)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/types")
    public Flux<RoomType> getAllRoomTypes() {
        return roomService.getAllRoomTypes();
    }


}
