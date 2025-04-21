package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.*;
import edu.miu.cs.cs489appsd.hotel.entities.User;
import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import edu.miu.cs.cs489appsd.hotel.exceptions.InvalidCredentialException;
import edu.miu.cs.cs489appsd.hotel.exceptions.NotFoundException;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.UserRepository;
import edu.miu.cs.cs489appsd.hotel.security.AuthUser;
import edu.miu.cs.cs489appsd.hotel.security.JwtUtils;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<Response> registerUser(RegistrationRequest registrationRequest) {
        UserRole role = registrationRequest.getRole() != null ? registrationRequest.getRole() : UserRole.CUSTOMER;

        User userToSave = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .isActive(Boolean.TRUE)
                .build();

        return userRepository.save(userToSave)
                .thenReturn(Response.builder()
                        .status(201)
                        .message("User registered successfully")
                        .build());
    }

    @Override
    public Mono<Response> loginUser(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
                .switchIfEmpty(Mono.error(new NotFoundException("Email not found")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        return Mono.error(new InvalidCredentialException("Password doesn't match"));
                    }

                    String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
                    return Mono.just(Response.builder()
                            .status(200)
                            .message("User logged in successfully")
                            .role(user.getRole())
                            .token(token)
                            .isActive(user.getIsActive())
                            .expirationTime("6 months")
                            .build());
                });
    }


    @Override
    public Mono<Response> getAllUsers() {
        log.info("Attempting to get all users");
        return getCurrentLoggedInUser()
                .doOnNext(user -> log.info("Current user role: {}", user.getRole()))
                .flatMap(currentUser -> {
                    if (currentUser.getRole() != UserRole.ADMIN) {
                        log.warn("Access denied for non-admin user: {}", currentUser.getEmail());
                        return Mono.error(new AccessDeniedException("Only ADMINs allowed"));
                    }
                    return userRepository.findAll()
                            .collectList()
                            .doOnNext(users -> log.info("Found {} users", users.size()))
                            .map(users -> {
                                List<UserDto> userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
                                return Response.builder()
                                        .status(200)
                                        .message("Users retrieved successfully")
                                        .users(userDtos)
                                        .build();
                            });
                })
                .doOnError(e -> log.error("Error in getAllUsers: {}", e.getMessage()));
    }

    @Override
    public Mono<Response> getOwnAccountDetails() {
        return getCurrentLoggedInUser()
                .map(user -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    return Response.builder()
                            .status(200)
                            .message("User account details retrieved successfully")
                            .user(userDto)
                            .build();
                });
    }

    @Override
    public Mono<Response> updateOwnAccount(UserDto userDto) {
        return getCurrentLoggedInUser()
                .flatMap(existingUser -> {
                    log.info("Updating user with email: {}", existingUser.getEmail());
                    if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());
                    if (userDto.getFirstName() != null) existingUser.setFirstName(userDto.getFirstName());
                    if (userDto.getLastName() != null) existingUser.setLastName(userDto.getLastName());
                    if (userDto.getPhoneNumber() != null) existingUser.setPhoneNumber(userDto.getPhoneNumber());
                    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    }

                    return userRepository.save(existingUser)
                            .thenReturn(Response.builder()
                                    .status(200)
                                    .message("User updated successfully")
                                    .build());
                });
    }

    @Override
    public Mono<User> getCurrentLoggedInUser() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new NotFoundException("No security context found")))
                .flatMap(context -> {
                    if (context.getAuthentication() == null) {
                        return Mono.error(new NotFoundException("No authentication found"));
                    }
                    Object principal = context.getAuthentication().getPrincipal();
                    if (principal instanceof AuthUser authUser) {
                        return userRepository.findByEmail(authUser.getUsername())
                                .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
                    } else if (principal instanceof String username) {
                        return userRepository.findByEmail(username)
                                .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
                    } else {
                        return Mono.error(new NotFoundException("Unexpected principal type"));
                    }
                });
    }

    @Override
    public Mono<Response> deleteOwnAccount() {
        return getCurrentLoggedInUser()
                .flatMap(user -> userRepository.delete(user)
                        .thenReturn(Response.builder()
                                .status(200)
                                .message("User deleted successfully")
                                .build()));
    }

    @Override
    public Mono<Response> getMyBookingHistory() {
        return getCurrentLoggedInUser()
                .flatMap(user -> bookingRepository.findByUserId(user.getId())
                        .collectList()
                        .map(bookingList -> {
                            List<BookingDto> bookingDtos = modelMapper.map(bookingList,
                                    new TypeToken<List<BookingDto>>() {}.getType());
                            return Response.builder()
                                    .status(200)
                                    .message("successful")
                                    .bookings(bookingDtos)
                                    .build();
                        }));
    }
}
