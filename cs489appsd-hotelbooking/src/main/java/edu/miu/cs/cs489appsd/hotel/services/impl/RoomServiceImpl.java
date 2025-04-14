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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/room-images/";

    @Override
    public Response addRoom(RoomDto roomDto, MultipartFile imageFile) {
        Room roomToSave = modelMapper.map(roomDto, Room.class);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            roomToSave.setImageUrl(imageUrl);
        }

        roomRepository.save(roomToSave);

        return Response.builder()
                .status(201)
                .message("Room added successfully")
                .build();
    }

    @Override
    public Response updateRoom(RoomDto roomDto, MultipartFile imageFile) {
        Room existingRoom = roomRepository.findById(roomDto.getId())
                .orElseThrow(() -> new NotFoundException("Room not found"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            existingRoom.setImageUrl(imagePath);
        }

        if (roomDto.getRoomNumber() != null && roomDto.getRoomNumber() >= 0) {
            existingRoom.setRoomNumber(roomDto.getRoomNumber());
        }

        if (roomDto.getPricePerNight() != null && roomDto.getPricePerNight().compareTo(BigDecimal.ZERO) >= 0) {
            existingRoom.setPricePerNight(roomDto.getPricePerNight());
        }

        if (roomDto.getCapacity() != null && roomDto.getCapacity() > 0) {
            existingRoom.setCapacity(roomDto.getCapacity());
        }

        if (roomDto.getRoomType() != null) existingRoom.setRoomType(roomDto.getRoomType());
        if (roomDto.getDescription() != null) existingRoom.setDescription(roomDto.getDescription());

        roomRepository.save(existingRoom);
        return Response.builder()
                .status(200)
                .message("Room added successfully")
                .build();
    }


    @Override
    public Response getAllRooms() {
        List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken <List<RoomDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Rooms retrieved successfully")
                .rooms(roomDtoList)
                .build();
    }

    @Override
    public Response getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        RoomDto roomDto = modelMapper.map(room, RoomDto.class);

        return Response.builder()
                .status(200)
                .message("success")
                .room(roomDto)
                .build();
    }

    @Override
    public Response deleteRoom(Long id) {
        // Step 1: Retrieve the room entity
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        // Step 2: Delete the image file if it exists
        if (room.getImageUrl() != null) {
            String imagePath = IMAGE_DIRECTORY + room.getImageUrl();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete(); // delete quietly
            }
        }

        // Step 3: Delete the room from DB
        roomRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Room Deleted Successfully")
                .build();
    }

    @Override
    public Response getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType) {
        // Validation: Ensure the check-in date is not before today
        if(checkInDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be before today");
        }
        // Validation: Ensure the check-out date is not before check-in date
        if(checkOutDate.isBefore(checkInDate)) {
            throw new InvalidBookingStateAndDateException("Check-out date cannot be before check-in date");
        }
        // Validation: Ensure the check-out date is not before check-in date
        if(checkInDate.isEqual(checkOutDate)) {
            throw new InvalidBookingStateAndDateException("Check-in date cannot be same as check-out date");
        }

        List<Room> roomList = roomRepository.findAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken <List<RoomDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Available rooms retrieved successfully")
                .rooms(roomDtoList)
                .build();

    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return roomRepository.getAllRoomTypes();
    }

    @Override
    public Response searchRoom(String searchParam) {
        List<Room> roomList = roomRepository.searchRooms(searchParam);
        List<RoomDto> roomDtoList = modelMapper.map(roomList, new TypeToken <List<RoomDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Available rooms retrieved successfully")
                .rooms(roomDtoList)
                .build();
    }

    private String saveImage(MultipartFile imageFile) {
        if (!Objects.requireNonNull(imageFile.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("Only images are allowed");
        }

        // Create a directory to store an image if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        // Get the absolute path of the image file
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            // Save the image file to the specified path
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return uniqueFileName;
    }


}
