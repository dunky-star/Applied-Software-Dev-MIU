package edu.miu.cs.cs489appsd.hotel.security;

import edu.miu.cs.cs489appsd.hotel.exceptions.NotFoundException;
import edu.miu.cs.cs489appsd.hotel.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with username: " + username)))
                .map(user -> AuthUser.builder()
                        .user(user)
                        .build());
    }
}
