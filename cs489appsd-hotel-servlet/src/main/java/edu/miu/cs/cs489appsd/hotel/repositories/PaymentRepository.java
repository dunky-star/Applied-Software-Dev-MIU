package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
