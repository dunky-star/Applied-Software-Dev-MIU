package edu.cs489app.library.dto.response;

public record AuthorResponseDto(
        Long id,
        String firstName,
        String lastName,
        AddressResponseDto addressResponseDto
) {
}
