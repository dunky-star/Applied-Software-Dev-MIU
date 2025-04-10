package edu.cs489app.library.dto.response;

public record AddressResponseDto(
        Long id,
        String unitNo,
        String street,
        String city,
        String state,
        Integer zip
) {
}
