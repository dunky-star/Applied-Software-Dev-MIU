package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDto {
    private Long id;
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "Recipient is required")
    private String recipient;
    private String body;
    private String bookingReference;
    private NotificationType type;
    private LocalDateTime createdAt;
}
