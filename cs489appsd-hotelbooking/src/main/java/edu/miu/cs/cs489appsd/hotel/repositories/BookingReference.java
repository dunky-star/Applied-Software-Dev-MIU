package edu.miu.cs.cs489appsd.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingReference extends JpaRepository<BookingReference, Long> {
    Optional<BookingReference> findByReferenceNo(String referenceNo);
}
