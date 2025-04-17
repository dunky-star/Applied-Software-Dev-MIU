package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.entities.BookingReference;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingReferenceRepository;
import edu.miu.cs.cs489appsd.hotel.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {
    private final BookingRepository bookingRepository;
    private final BookingReferenceRepository bookingReferenceRepository;

    public String generateBookingReference() {
        String bookingReference;

        // keep generating until a unique code is found
        do {
            bookingReference = generateRandomAlphaNumericCode(10); //generate code of length 10
        } while (isBookingReferenceExists(bookingReference)); //check if the code already exists. if it doesn't, exit

        saveBookingReferenceToDatabase(bookingReference); //save the code to database
        return bookingReference;
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

    private boolean isBookingReferenceExists(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference).isPresent();
    }

    private void saveBookingReferenceToDatabase(String bookingReference){
        BookingReference newBookingReference = BookingReference.builder().referenceNo(bookingReference).build();
        bookingReferenceRepository.save(newBookingReference);
    }
}
