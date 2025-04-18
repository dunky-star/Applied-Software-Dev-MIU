package edu.miu.cs.cs489appsd.hotel.entities;

import edu.miu.cs.cs489appsd.hotel.enums.PaymentGateway;
import edu.miu.cs.cs489appsd.hotel.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "payments") // R2DBC mapping
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id // R2DBC ID
    private Long id;

    @Column("transaction_id")
    private String transactionId;

    private BigDecimal amount;

    @Column("payment_gateway")
    private PaymentGateway paymentGateway;

    @Column("payment_date")
    private LocalDateTime paymentDate;

    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("booking_reference")
    private String bookingReference;

    @Column("failure_reason")
    private String failureReason;

    @Column("user_id") // Foreign key reference by ID (no @ManyToOne in R2DBC)
    private Long userId;
}

