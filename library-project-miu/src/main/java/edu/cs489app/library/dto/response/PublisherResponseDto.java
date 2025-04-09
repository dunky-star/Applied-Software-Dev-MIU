package edu.cs489app.library.dto.response;

public record PublisherResponseDto(
        Long id,
        String name,
        AddressResponseDto addressResponseDto
) {
}
