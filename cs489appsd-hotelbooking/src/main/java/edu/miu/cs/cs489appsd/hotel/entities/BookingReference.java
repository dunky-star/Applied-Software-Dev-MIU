package edu.miu.cs.cs489appsd.hotel.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "booking_references") // R2DBC table mapping
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingReference {

    @Id // R2DBC primary key
    private Long id;

    @Column("reference_no") // explicit column mapping
    private String referenceNo;
}
