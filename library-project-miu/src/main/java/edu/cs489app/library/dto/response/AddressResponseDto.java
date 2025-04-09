package edu.cs489app.library.dto.response;

public record AddressResponseDto(
        Long id,
        String unitNo,
        String city,
        String state,
        String zipCode
) {
}
