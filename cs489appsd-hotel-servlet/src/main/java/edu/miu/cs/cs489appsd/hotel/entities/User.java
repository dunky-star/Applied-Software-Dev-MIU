package edu.miu.cs.cs489appsd.hotel.entities;

import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @Column(name="phone_number", unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role; // e.g.: CUSTOMER, ADMIN
    private Boolean isActive;
    private final LocalDateTime createdAt = LocalDateTime.now();
}
