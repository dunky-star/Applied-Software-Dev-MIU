package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.*;
import edu.miu.cs.cs489appsd.hotel.entities.Booking;
import edu.miu.cs.cs489appsd.hotel.entities.User;
import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import edu.miu.cs.cs489appsd.hotel.exceptions.InvalidCredentialException;
import edu.miu.cs.cs489appsd.hotel.exceptions.NotFoundException;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.UserRepository;
import edu.miu.cs.cs489appsd.hotel.security.JwtUtils;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    @Override
    public Response registerUser(RegistrationRequest registrationRequest) {
        UserRole role = UserRole.CUSTOMER;
        if (registrationRequest.getRole() != null) {
            role = registrationRequest.getRole();
        }
        User userToSave = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .isActive(Boolean.TRUE)
                .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(201)
                .message("User registered successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Password doesn't match");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return Response.builder()
                .status(200)
                .message("User logged in successfully")
                .role(user.getRole())
                .token(token)
                .isActive(user.getIsActive())
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));

        List<UserDto> userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Users retrieved successfully")
                .users(userDtos)
                .build();
    }

    @Override
    public Response getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return Response.builder()
                .status(200)
                .message("User account details retrieved successfully")
                .user(userDto)
                .build();
    }

    @Override
    public Response updateOwnAccount(UserDto userDto) {
        User exsitingUser = getCurrentLoggedInUser();
        log.info("Updating user with email: {}", exsitingUser.getEmail());
        if(userDto.getEmail() != null) exsitingUser.setEmail(userDto.getEmail());
        if(userDto.getFirstName() != null) exsitingUser.setFirstName(userDto.getFirstName());
        if(userDto.getLastName() != null) exsitingUser.setLastName(userDto.getLastName());
        if(userDto.getPhoneNumber() != null) exsitingUser.setPhoneNumber(userDto.getPhoneNumber());

        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            exsitingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(exsitingUser);

        return Response.builder()
                .status(200)
                .message("User updated successfully")
                .build();
    }

    // To be used in other parts of the application to manage sessions but not in controller
    @Override
    public User getCurrentLoggedInUser() {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       return userRepository.findByEmail(email)
               .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public Response deleteOwnAccount() {
        User user = getCurrentLoggedInUser();
        userRepository.delete(user);
        return Response.builder()
                .status(200)
                .message("User deleted successfully")
                .build();
    }

    @Override
    public Response getMyBookingHistory() {
        User user = getCurrentLoggedInUser();
        List<Booking> bookingList = bookingRepository.findByUserId(user.getId());
        List<BookingDto> bookingDto = modelMapper.map(bookingList, new TypeToken<List<BookingDto>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("successful")
                .bookings(bookingDto)
                .build();
    }
}
