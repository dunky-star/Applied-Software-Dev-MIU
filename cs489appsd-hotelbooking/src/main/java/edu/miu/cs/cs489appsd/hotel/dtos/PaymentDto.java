package edu.miu.cs.cs489appsd.hotel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.cs.cs489appsd.hotel.enums.PaymentGateway;
import edu.miu.cs.cs489appsd.hotel.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private BookingDto booking;
    private String transactionId;
    private BigDecimal amount;
    private PaymentGateway paymentMethod; // e.g., VISA_DEBIT, PAYPAL, STRIPE
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String bookingReference;
    private String failureReason;
    private String approvalLink;
}


