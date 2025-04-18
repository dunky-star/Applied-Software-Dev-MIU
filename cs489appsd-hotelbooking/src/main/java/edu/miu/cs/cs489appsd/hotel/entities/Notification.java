package edu.miu.cs.cs489appsd.hotel.entities;

import edu.miu.cs.cs489appsd.hotel.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "notifications") // R2DBC table mapping
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id // R2DBC @Id
    private Long id;

    private String subject;

    @NotBlank(message = "Recipient is required")
    private String recipient;

    private String body;

    @Column("booking_reference")
    private String bookingReference;

    @Column("notification_type")
    private NotificationType notificationType;

    @Column("created_at")
    private LocalDateTime createdAt;
}

