package edu.miu.cs.cs489appsd.hotel;

import edu.miu.cs.cs489appsd.hotel.dtos.LoginRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.RegistrationRequest;
import edu.miu.cs.cs489appsd.hotel.entities.User;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.UserRepository;
import edu.miu.cs.cs489appsd.hotel.security.JwtUtils;
import edu.miu.cs.cs489appsd.hotel.services.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private RegistrationRequest registrationRequest;
    private LoginRequest loginRequest;
    private User mockUser;





}
