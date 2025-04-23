package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.RoomDto;
import edu.miu.cs.cs489appsd.hotel.entities.Room;
import edu.miu.cs.cs489appsd.hotel.enums.RoomType;
import edu.miu.cs.cs489appsd.hotel.exceptions.InvalidBookingStateAndDateException;
import edu.miu.cs.cs489appsd.hotel.exceptions.NotFoundException;
import edu.miu.cs.cs489appsd.hotel.repositories.RoomRepository;
import edu.miu.cs.cs489appsd.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/room-images/";

    @Override
    public Mono<Response> addRoom(RoomDto roomDto, FilePart imageFile) {
        Room roomToSave = modelMapper.map(roomDto, Room.class);

        Mono<String> imageSaveMono = (imageFile != null)
                ? saveImage(imageFile)
                : Mono.just(""); // empty image path if no file provided

        return imageSaveMono
                .doOnNext(imageUrl -> {
                    if (!imageUrl.isBlank()) {
                        roomToSave.setImageUrl(imageUrl);
                    }
                })
                .then(roomRepository.save(roomToSave))
                .thenReturn(Response.builder()
                        .status(201)
                        .message("Room added successfully")
                        .build());
    }

    @Override
    public Mono<Response> updateRoom(RoomDto roomDto, FilePart imageFile) {
        return roomRepository.findById(roomDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Room not found")))
                .flatMap(existingRoom -> {
                    Mono<String> imageSaveMono = (imageFile != null)
                            ? saveImage(imageFile)
                            : Mono.just(""); // no image provided

                    return imageSaveMono.flatMap(imageUrl -> {
                        if (!imageUrl.isBlank()) {
                            existingRoom.setImageUrl(imageUrl);
                        }

                        if (roomDto.getRoomNumber() != null && roomDto.getRoomNumber() >= 0) {
                            existingRoom.setRoomNumber(roomDto.getRoomNumber());
                        }

                        if (roomDto.getPricePerNight() != null &&
                                roomDto.getPricePerNight().compareTo(BigDecimal.ZERO) >= 0) {
                            existingRoom.setPricePerNight(roomDto.getPricePerNight());
                        }

                        if (roomDto.getCapacity() != null && roomDto.getCapacity() > 0) {
                            existingRoom.setCapacity(roomDto.getCapacity());
                        }

                        if (roomDto.getRoomType() != null) {
                            existingRoom.setRoomType(roomDto.getRoomType());
                        }

                        if (roomDto.getDescription() != null) {
                            existingRoom.setDescription(roomDto.getDescription());
                        }

                        return roomRepository.save(existingRoom);
                    });
                })
                .thenReturn(Response.builder()
                        .status(200)
                        .message("Room updated successfully")
                        .build());
    }


    @Override
    public Mono<Response> getAllRooms() {
        return roomRepository.findAll()
                .collectList()
                .map(roomList -> {
                    List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken<List<RoomDto>>() {}.getType());
                    return Response.builder()
                            .status(200)
                            .message("Rooms retrieved successfully")
                            .rooms(roomDtoList)
                            .build();
                });
    }

    @Override
    public Mono<Response> getRoomById(Long id) {
        return roomRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Room not found")))
                .map(room -> {
                    RoomDto roomDto = modelMapper.map(room, RoomDto.class);
                    return Response.builder()
                            .status(200)
                            .message("Success")
                            .room(roomDto)
                            .build();
                });
    }

    @Override
    public Mono<Response> deleteRoom(Long id) {
        return roomRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Room not found")))
                .flatMap(room -> {
                    if (room.getImageUrl() != null) {
                        String imagePath = IMAGE_DIRECTORY + room.getImageUrl();
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            imageFile.delete(); // delete quietly
                        }
                    }
                    return roomRepository.deleteById(id)
                            .then(Mono.just(Response.builder()
                                    .status(200)
                                    .message("Room Deleted Successfully")
                                    .build()));
                });
    }

    @Override
    public Mono<Response> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType) {
        validateBookingDates(checkInDate, checkOutDate);

        return roomRepository.findAvailableRooms(checkInDate, checkOutDate, roomType)
                .collectList()
                .map(roomList -> {
                    List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken<List<RoomDto>>() {}.getType());
                    return Response.builder()
                            .status(200)
                            .message("Available rooms retrieved successfully")
                            .rooms(roomDtoList)
                            .build();
                });
    }

    @Override
    public Flux<RoomType> getAllRoomTypes() {
        return roomRepository.getAllRoomTypes();
    }

    @Override
    public Mono<Response> searchRoom(String searchParam) {
        return roomRepository.searchRooms(searchParam)
                .collectList()
                .map(roomList -> {
                    List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken<List<RoomDto>>() {}.getType());
                    return Response.builder()
                            .status(200)
                            .message("Rooms retrieved successfully")
                            .rooms(roomDtoList)
                            .build();
                });
    }

    private void validateBookingDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be before today");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new InvalidBookingStateAndDateException("Check-out date cannot be before check-in date");
        }
        if (checkInDate.isEqual(checkOutDate)) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be the same as check-out date");
        }
    }

    private Mono<String> saveImage(FilePart imageFile) {
        String contentType = imageFile.headers().getContentType() != null
                ? imageFile.headers().getContentType().toString()
                : "";

        // Validate file type
        if (!contentType.startsWith("image/")) {
            return Mono.error(new IllegalArgumentException("Only images are allowed"));
        }

        // Ensure the directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename and full image path
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.filename();
        Path imagePath = Paths.get(IMAGE_DIRECTORY, uniqueFileName);

        // Save the image asynchronously and return the file name
        return imageFile.transferTo(imagePath)
                .thenReturn(uniqueFileName)
                .onErrorMap(ex -> new IllegalArgumentException("Failed to save image: " + ex.getMessage()));
    }

}

