package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmail(String email);
}
