package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.BookingReference;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BookingReferenceRepository extends ReactiveCrudRepository<BookingReference, Long> {
    Mono<BookingReference> findByReferenceNo(String referenceNo);
}
