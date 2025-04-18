package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.entities.BookingReference;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingReferenceRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {
    private final BookingRepository bookingRepository;
    private final BookingReferenceRepository bookingReferenceRepository;

    public Mono<String> generateBookingReference() {
        return generateUniqueBookingReference()
                .flatMap(this::saveBookingReferenceToDatabase);
    }

    private Mono<String> generateUniqueBookingReference() {
        String bookingReference = generateRandomAlphaNumericCode(10);

        return bookingRepository.findByBookingReference(bookingReference)
                .flatMap(existing -> generateUniqueBookingReference()) // If exists, generate again
                .switchIfEmpty(Mono.just(bookingReference));            // If not exists, use it
    }

    private String generateRandomAlphaNumericCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();
    }

    private Mono<String> saveBookingReferenceToDatabase(String bookingReference) {
        BookingReference newBookingReference = BookingReference.builder()
                .referenceNo(bookingReference)
                .build();
        return bookingReferenceRepository.save(newBookingReference)
                .thenReturn(bookingReference);
    }
}
