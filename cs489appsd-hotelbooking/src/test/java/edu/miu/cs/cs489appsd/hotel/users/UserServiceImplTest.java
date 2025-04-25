package edu.miu.cs.cs489appsd.hotel.users;

import edu.miu.cs.cs489appsd.hotel.dtos.LoginRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.RegistrationRequest;
import edu.miu.cs.cs489appsd.hotel.entities.User;
import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.UserRepository;
import edu.miu.cs.cs489appsd.hotel.security.JwtUtils;
import edu.miu.cs.cs489appsd.hotel.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setup() {
        registrationRequest = RegistrationRequest.builder()
                .firstName("Geoffrey")
                .lastName("Opiyo")
                .email("geo.opiyo@example.com")
                .password("password123")
                .phoneNumber("1234567890")
                .build();

        loginRequest = new LoginRequest("geo.opiyo@example.com", "password123");

        mockUser = User.builder()
                .id(1L)
                .firstName("Geoffrey")
                .lastName("Duncan")
                .email("geo.opiyo@example.com")
                .password("hashedPassword")
                .phoneNumber("1234567890")
                .role(UserRole.CUSTOMER)
                .isActive(true)
                .build();
    }

    @Test
    void registerUser_successful() {
        // encode password, save user, return Response
    }

    @Test
    void loginUser_successful() {
        // email exists, password matches, token generated
    }

    @Test
    void loginUser_wrongPassword_shouldThrow() {
        // password mismatch => InvalidCredentialException
    }

    @Test
    void getAllUsers_asAdmin_returnsList() {
        // simulate ADMIN context, return all users
    }

    @Test
    void getAllUsers_asCustomer_accessDenied() {
        // simulate CUSTOMER => throw AccessDeniedException
    }

    @Test
    void updateOwnAccount_successful() {
        // simulate user context, change fields, assert response
    }

    @Test
    void deleteOwnAccount_successful() {
        // simulate user context, call delete
    }

    @Test
    void getCurrentLoggedInUser_fromSecurityContext() {
        // mock ReactiveSecurityContextHolder to return AuthUser
    }

}
