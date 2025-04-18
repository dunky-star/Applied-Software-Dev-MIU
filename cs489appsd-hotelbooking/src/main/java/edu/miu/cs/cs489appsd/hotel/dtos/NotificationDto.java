package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record NotificationDto(
        Long id,

        @NotBlank(message = "Subject is required")
        String subject,

        @NotBlank(message = "Recipient is required")
        String recipient,

        String body,
        String bookingReference,
        NotificationType type,
        LocalDateTime createdAt
) { }
