package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.LoginRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.RegistrationRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.UserDto;
import edu.miu.cs.cs489appsd.hotel.entities.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Response> registerUser(RegistrationRequest registrationRequest);
    Mono<Response> loginUser(LoginRequest loginRequest);
    Mono<Response> getAllUsers();
    Mono<Response> getOwnAccountDetails();
    Mono<Response> updateOwnAccount(UserDto userDto);
    Mono<User> getCurrentLoggedInUser(); // because you may want User entity internally, not DTO;
    Mono<Response> deleteOwnAccount();
    Mono<Response> getMyBookingHistory();
}
