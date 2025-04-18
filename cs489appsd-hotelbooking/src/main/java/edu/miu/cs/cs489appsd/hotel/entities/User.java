package edu.miu.cs.cs489appsd.hotel.entities;

import edu.miu.cs.cs489appsd.hotel.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("users") // <-- R2DBC uses @Table from spring-data-relational
public class User {

    @Id
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column("email") // optional, helps map exactly
    private String email;

    @NotBlank(message = "Password is required")
    @Column("password")
    private String password;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Column("phone_number")
    private String phoneNumber;

    @Column("role")
    private UserRole role; // ENUM still fine — just store as VARCHAR or TEXT in Postgres

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // Important: use @Builder.Default when default value
}
